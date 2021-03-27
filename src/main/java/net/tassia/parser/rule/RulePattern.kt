package net.tassia.parser.rule

import kotlin.reflect.KProperty

sealed class RulePattern(var quantifier: Quantifier) {

	abstract override fun toString(): String

	operator fun getValue(owner: Any, property: KProperty<*>): Rule {
		return Rule(property.name, this)
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
