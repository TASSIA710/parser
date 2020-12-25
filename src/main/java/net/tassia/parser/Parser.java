package net.tassia.parser;

import net.tassia.parser.rule.RuleSet;
import net.tassia.parser.token.TokenProvider;
import net.tassia.parser.token.TokenType;

/**
 * A parser is responsible for using a given {@link RuleSet} to parse a {@link String}.
 * @since Parser 1.0
 * @author Tassilo
 */
public abstract class Parser {

	protected final TokenProvider provider;
	protected final RuleSet rules;

	/**
	 * Creates a new parser using the given {@link TokenProvider} and {@link RuleSet}.
	 * @param provider the token provider
	 * @param rules the rules
	 */
	public Parser(TokenProvider provider, RuleSet rules) {
		this.provider = provider;
		this.rules = rules;
	}

	/**
	 * Parses the given source string.
	 * @param source the source string
	 * @return the parsed token
	 * @throws ParseException if, for example, an unexpected token was encountered
	 */
	public abstract TokenType parse(String source) throws ParseException;

}
