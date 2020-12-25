package net.tassia.parser.token;

import net.tassia.parser.rule.Rule;

/**
 * Provides the parser with alternative TokenType instances to make more sense of the final result.
 */
@FunctionalInterface
public interface TokenProvider {

	/**
	 * Provides a new token type for the given rule and the currently calculated token type.
	 * @param rule the rule
	 * @param raw the raw token
	 * @return a new token type
	 */
	TokenType provide(Rule rule, TokenType raw);



	/**
	 * This is the simplest TokenProvider. It just uses the auto-generated raw token type.
	 */
	TokenProvider NO_OPERATION = (rule, raw) -> raw;

}
