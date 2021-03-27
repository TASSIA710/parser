package net.tassia.parser.token

import net.tassia.parser.rule.Rule

/**
 * Provides the parser with alternative [TokenType] instances to make more sense of the final result.
 *
 * @since Parser 1.0
 * @author Tassilo
 */
fun interface TokenProvider {

	/**
	 * Provides a new token type for the given rule and the currently calculated token type.
	 *
	 * @param rule the rule
	 * @param raw the raw token
	 * @return a new token type
	 */
	fun provide(rule: Rule, raw: TokenType): TokenType



	companion object {

		/**
		 * The simplest TokenProvider. It returns the auto-generated raw token type.
		 */
		val NO_OPERATION = TokenProvider { _, raw -> raw }

	}

}
