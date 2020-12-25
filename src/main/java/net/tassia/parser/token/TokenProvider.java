package net.tassia.parser.token;

import net.tassia.parser.rule.Rule;

@FunctionalInterface
public interface TokenProvider {

	TokenType provide(Rule rule, TokenType raw);

}
