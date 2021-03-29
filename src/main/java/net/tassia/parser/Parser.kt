package net.tassia.parser

import net.tassia.parser.rule.Rule
import net.tassia.parser.rule.RulePattern
import net.tassia.parser.token.TokenProvider
import net.tassia.parser.token.TokenType

/**
 * A parser is used to parse a string into a token.
 *
 * @since Parser 1.0
 * @author Tassilo
 */
abstract class Parser(val provider: TokenProvider) {

	/**
	 * Parses the given source string for the given rule.
	 *
	 * @param source the source string to parse
	 * @param rule the rule to parse for
	 * @param offset the offset to start parsing at
	 * @return the parsed token
	 *
	 * @throws ParseException if, for example, an unexpected token was encountered
	 */
	@Throws(ParseException::class)
	abstract fun parse(source: String, rule: Rule, offset: Int = 0): TokenType

	/**
	 * Parses the given source string for the given rule.
	 * If the string has not been fully read after the rule
	 * has been parsed, a ParseException is thrown, expecting an EOF.
	 *
	 * @param source the source string to pasrse
	 * @param rule the rule to parse for
	 * @param offset the offset to start parsing at
	 * @return the parsed token
	 *
	 * @throws ParseException if, for example, an unexpected token was encountered
	 */
	@Throws(ParseException::class)
	abstract fun parseFull(source: String, rule: Rule, offset: Int = 0): TokenType

}
