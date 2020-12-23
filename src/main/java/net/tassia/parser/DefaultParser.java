package net.tassia.parser;

import net.tassia.parser.rule.RulePattern;
import net.tassia.parser.rule.RuleSet;

import java.util.ArrayList;

/**
 * This might be the smallest implementation of a BNF parser.
 * This is because this does not strictly follow any standards.
 * @since Parser 1.0
 * @author Tassilo
 */
public class DefaultParser extends Parser {

	protected RuleSet rules;
	protected String source;
	protected int pos;

	@Override
	public void parse(RuleSet rules, String source) throws ParseException {
		// Reset
		this.rules = rules;
		this.source = source;
		this.pos = 0;

		// Read root rule
		readRulePattern(rules.getRoot().getPattern());

		// End
		if (pos == source.length()) return;
		throw new ParseException("Expected EOF", source, pos, source.length() - pos);
	}



	protected void readRulePattern(RulePattern pattern) throws ParseException {
		switch (pattern.getQuantifier()) {
			case ONCE -> {
				// Read the pattern exactly once
				var token = readRulePatternOnce(pattern);
				// TODO

			}
			case OPTIONAL -> {
				// Read the pattern exactly once
				// If it fails, continue with null
				var startPos = pos;
				TokenType token;
				try {
					token = readRulePatternOnce(pattern);
				} catch (ParseException ignored) {
					pos = startPos;
					token = null;
				}
				// TODO

			}
			case MULTIPLE -> {
				// Read the pattern at least one time
				var tokens = new ArrayList<TokenType>();
				tokens.add(readRulePatternOnce(pattern));
				while (true) {
					var startPos = pos;
					try {
						tokens.add(readRulePatternOnce(pattern));
					} catch (ParseException ignored) {
						pos = startPos;
						break;
					}
				}
				// TODO

			}
			case ANY -> {
				// Read the pattern as often as possible
				var tokens = new ArrayList<TokenType>();
				while (true) {
					var startPos = pos;
					try {
						tokens.add(readRulePatternOnce(pattern));
					} catch (ParseException ignored) {
						pos = startPos;
						break;
					}
				}
				// TODO

			}
			default -> {
				throw new ParseException("Unknown Quantifier: " + pattern.getQuantifier());
			}
		}
	}

	protected TokenType readRulePatternOnce(RulePattern pattern) throws ParseException {
		if (pattern instanceof RulePattern.ConstantString) {
			var cast = (RulePattern.ConstantString) pattern;
			var str = cast.getValue();
			if (source.startsWith(str, pos)) {
				pos += str.length();
				return new TokenType();
			} else {
				var got = substring(pos, pos + str.length());
				throw new ParseException("Expected '" + str + "' but got '" + got + "'", source, pos, str.length());
			}

		} else if (pattern instanceof RulePattern.RuleCall) {
			var cast = (RulePattern.RuleCall) pattern;
			var rule = rules.getRule(cast.getRule());
			if (rule == null) {
				throw new ParseException("Unknown Rule: " + cast.getRule());
			}
			readRulePattern(rule.getPattern());
			// TODO
			return new TokenType();

		} else if (pattern instanceof RulePattern.MultiplePossible) {
			var cast = (RulePattern.MultiplePossible) pattern;
			for (var p : cast.getPatterns()) {
				var startPos = pos;
				try {
					readRulePattern(p);
					// TODO
					return new TokenType();
				} catch (ParseException ignored) {
					pos = startPos;
				}
			}
			var msg = new StringBuilder();
			for (var p : cast.getPatterns()) {
				msg.append(" or ").append(p.toString());
			}
			throw new ParseException("Expected " + msg.substring(4), source, pos, 1);

		} else if (pattern instanceof RulePattern.Chained) {
			var cast = (RulePattern.Chained) pattern;
			var tokens = new ArrayList<TokenType>();
			for (var p : cast.getPatterns()) {
				readRulePattern(p);
				// TODO
			}
			// TODO
			return new TokenType();

		} else {
			throw new ParseException("Unknown RulePattern: " + pattern.toString());
		}
	}

	private String substring(int pos, int end) {
		pos = Math.min(pos, source.length());
		end = Math.min(end, source.length());
		return source.substring(pos, end);
	}

}
