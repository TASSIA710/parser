package net.tassia.parser.rule

import net.tassia.parser.token.TokenProvider
import net.tassia.parser.token.TokenType
import kotlin.reflect.KProperty

sealed class RulePattern(var quantifier: Quantifier) {

	private var tokenProvider: TokenProvider = TokenProvider.NO_OPERATION

	abstract override fun toString(): String

	operator fun getValue(owner: Any, property: KProperty<*>): Rule {
		return Rule(property.name, this, tokenProvider)
	}

	infix fun token(tokenProvider: (TokenType) -> TokenType): RulePattern {
		this.tokenProvider = TokenProvider { _, raw -> tokenProvider(raw) }
		return this
	}

}



class ChainedPattern(val patterns: Array<RulePattern>, quantifier: Quantifier) : RulePattern(quantifier) {

	override fun toString(): String {
		return "(" + patterns.joinToString(separator = " ", transform = RulePattern::toString) + ")"
	}

}



class MultiplePossiblePattern(val patterns: Array<RulePattern>, quantifier: Quantifier) : RulePattern(quantifier) {

	override fun toString(): String {
		return patterns.joinToString(separator = " | ", transform = RulePattern::toString)
	}

}



class RuleCallPattern(val rule: Rule, quantifier: Quantifier) : RulePattern(quantifier) {

	override fun toString(): String {
		return "<${rule.name}>"
	}

}



class ConstantStringPattern(val value: String, quantifier: Quantifier) : RulePattern(quantifier) {

	override fun toString(): String {
		// TODO: Escape value
		return '"' + value + '"'
	}

}



class CharMatchPattern(val value: (Char) -> Boolean, quantifier: Quantifier) : RulePattern(quantifier) {

	override fun toString(): String {
		return "\$CM_PRED:$value$"
	}

}
