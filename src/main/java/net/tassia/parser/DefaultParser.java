package net.tassia.parser;

import net.tassia.parser.rule.Quantifier;
import net.tassia.parser.rule.RulePattern;
import net.tassia.parser.rule.RuleSet;
import net.tassia.parser.token.TokenProvider;
import net.tassia.parser.token.TokenType;

import java.util.ArrayList;

/**
 * This might be the smallest implementation of a BNF parser.
 * This is because this does not strictly follow any standards.
 * @since Parser 1.0
 * @author Tassilo
 */
public class DefaultParser extends Parser {

	protected String source;
	protected int pos;

	/**
	 * Creates a new parser using the given {@link TokenProvider} and {@link RuleSet}.
	 * @param provider the token provider
	 * @param rules the rules
	 */
	public DefaultParser(TokenProvider provider, RuleSet rules) {
		super(provider, rules);
	}

	@Override
	public TokenType parse(String source) throws ParseException {
		// Reset
		this.source = source;
		this.pos = 0;

		// Read root rule
		var token = readRulePattern(new RulePattern.RuleCall(Quantifier.ONCE, rules.getRoot().getName()));

		// End
		if (pos == source.length()) return token;
		throw new ParseException("Expected EOF", source, pos, source.length() - pos);
	}



	protected TokenType readRulePattern(RulePattern pattern) throws ParseException {
		switch (pattern.getQuantifier()) {
			case ONCE -> {
				// Read the pattern exactly once
				return readRulePatternOnce(pattern);

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
				return token;

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
				return new TokenType.Multiple(tokens.toArray(new TokenType[0]));

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
				return new TokenType.Multiple(tokens.toArray(new TokenType[0]));

			}
			default -> throw new ParseException("Unknown Quantifier: " + pattern.getQuantifier());
		}
	}

	protected TokenType readRulePatternOnce(RulePattern pattern) throws ParseException {
		if (pattern instanceof RulePattern.ConstantString) {
			var cast = (RulePattern.ConstantString) pattern;
			var str = cast.getValue();
			if (source.startsWith(str, pos)) {
				pos += str.length();
				return new TokenType.StringValue(str);
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
			var raw = readRulePattern(rule.getPattern());
			return provider.provide(rule, raw);

		} else if (pattern instanceof RulePattern.MultiplePossible) {
			var cast = (RulePattern.MultiplePossible) pattern;
			for (var p : cast.getPatterns()) {
				var startPos = pos;
				try {
					return readRulePattern(p);
				} catch (ParseException ignored) {
					pos = startPos;
				}
			}
			throw new ParseException("Expected " + cast.toString(), source, pos, 1);

		} else if (pattern instanceof RulePattern.Chained) {
			var cast = (RulePattern.Chained) pattern;
			var tokens = new ArrayList<TokenType>();
			for (var p : cast.getPatterns()) {
				tokens.add(readRulePattern(p));
			}
			return new TokenType.Multiple(tokens.toArray(new TokenType[0]));

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
