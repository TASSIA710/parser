package net.tassia.parser;

import net.tassia.parser.rule.Quantifier;
import net.tassia.parser.rule.Rule;
import net.tassia.parser.rule.RulePattern;
import net.tassia.parser.rule.RuleSet;

import java.util.ArrayList;

public class DefaultBNFParser extends BNFParser {

	@Override
	public RuleSet parse(String input) throws ParseException {
		var rules = new RuleSet();
		var lines = input.split("\n");
		for (var line : lines) {
			line = line.trim();
			var reader = new RuleReader(line);
			reader.read(rules);
		}
		return rules;
	}

	private static class RuleReader {
		private final String rule;
		private int position;

		private RuleReader(String rule) {
			this.rule = rule;
			this.position = 0;
		}

		private void read(RuleSet rules) throws ParseException {
			// Read rule identifier
			var id = readIdentifier();

			// Read assign operator
			skipWhitespace();
			if (!rule.startsWith("::=", position)) {
				throw new ParseException("Expected '::='", rule, position, 3);
			}
			position += 3;

			// Read pattern
			var pattern = readPattern();

			// Add rule
			var rule = new Rule(id, pattern);
			rules.addRule(rule);

			// Is root?
			if (rules.getRoot() == null) {
				rules.setRoot(rule);
			}
		}

		private RulePattern readPattern() throws ParseException {
			var patterns = new ArrayList<RulePattern>();
			patterns.add(readChainedPattern());
			skipWhitespace();
			while (peek() == '|') {
				position++;
				patterns.add(readChainedPattern());
				skipWhitespace();
			}
			return new RulePattern.MultiplePossible(Quantifier.ONCE, patterns.toArray(new RulePattern[0]));
		}

		private RulePattern readChainedPattern() throws ParseException {
			var patterns = new ArrayList<RulePattern>();
			while (position < rule.length() && peek() != ')' && peek() != '|') {
				skipWhitespace();
				patterns.add(readPatternSingle());
				skipWhitespace();
			}
			return new RulePattern.Chained(Quantifier.ONCE, patterns.toArray(new RulePattern[0]));
		}

		private RulePattern readPatternSingle() throws ParseException {
			if (peek() == '(') {
				position++;
				var pattern = readPattern();
				if (peek() != ')') {
					throw new ParseException("Expected ')", rule, position, 1);
				}
				position++;
				pattern.setQuantifier(readQuantifier());
				return pattern;

			} else if (peek() == '"') {
				var str = readConstantString();
				return new RulePattern.ConstantString(readQuantifier(), str);

			} else {
				var id = readIdentifier();
				return new RulePattern.RuleCall(readQuantifier(), id);

			}
		}

		private String readConstantString() throws ParseException {
			if (peek() != '"') {
				throw new ParseException("Expected '\"'", rule, position, 1);
			}
			position++; // Read starting '"'
			var sb = new StringBuilder();
			while (peek() != '"') {
				var c = peek();
				position++;
				if (c == '\\') {
					var c2 = peek();
					position++;
					switch (c2) {
						case 't' -> sb.append('\t');
						case 'b' -> sb.append('\b');
						case 'n' -> sb.append('\n');
						case 'r' -> sb.append('\r');
						case 'f' -> sb.append('\f');
						case '\'' -> sb.append('\'');
						case '"' -> sb.append('"');
						case '\\' -> sb.append('\\');
						default -> throw new ParseException("Unexpected '" + c2 + "'", rule, position - 1, 1);
					}
				}
				sb.append(c);
			}
			position++; // Read ending '"'
			return sb.toString();
		}

		private Quantifier readQuantifier() {
			if (peek() == '?') {
				position++;
				return Quantifier.OPTIONAL;
			} else if (peek() == '+') {
				position++;
				return Quantifier.MULTIPLE;
			} else if (peek() == '*') {
				position++;
				return Quantifier.ANY;
			} else {
				return Quantifier.ONCE;
			}
		}

		private void skipWhitespace() {
			while (Character.isWhitespace(peek())) {
				position++;
			}
		}

		private String readIdentifier() throws ParseException {
			var sb = new StringBuilder();
			if (peek() == '<') {
				position++;
				while (peek() != '>') {
					sb.append(peek());
					position++;
				}
				position++;
				return sb.toString();
			} else {
				throw new ParseException("Expected '<'", rule, position, 1);
			}
		}

		private char peek() {
			return position < rule.length() ? rule.charAt(position) : (char) 0;
		}

	}

}
