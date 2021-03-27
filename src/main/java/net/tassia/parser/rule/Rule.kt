package net.tassia.parser.rule

import net.tassia.parser.kotlin.once
import net.tassia.parser.token.TokenProvider
import net.tassia.parser.token.TokenType
import kotlin.reflect.KProperty

/**
 * Defines a rule (basically a single line of a BNF file).
 *
 * @param name the name of the rule
 * @param pattern the pattern of the rule
 *
 * @since Parser 1.0
 * @author Tassilo
 */
class Rule(

	/**
	 * The name of this rule.
	 */
	val name: String,

	/**
	 * The pattern of this rule.
	 */
	val pattern: RulePattern,

	/**
	 * A rule-specific custom token provider.
	 */
	tokenProvider: TokenProvider = TokenProvider.NO_OPERATION,

) {

	var tokenProvider: TokenProvider = tokenProvider
		private set

	infix fun token(tokenProvider: (TokenType) -> TokenType): RulePattern {
		return this.once().token(tokenProvider)
	}

	operator fun getValue(owner: Any, property: KProperty<*>): Rule {
		return Rule(property.name, RuleCallPattern(this, Quantifier.ONCE))
	}

}
