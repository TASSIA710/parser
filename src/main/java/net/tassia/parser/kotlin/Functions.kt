package net.tassia.parser.kotlin

import net.tassia.parser.rule.*



fun String.once() = ConstantStringPattern(this, Quantifier.ONCE)
fun String.optional() = ConstantStringPattern(this, Quantifier.OPTIONAL)
fun String.any() = ConstantStringPattern(this, Quantifier.ANY)
fun String.multiple() = ConstantStringPattern(this, Quantifier.MULTIPLE)

fun RulePattern.once() = this.also { it.quantifier = Quantifier.ONCE }
fun RulePattern.optional() = this.also { it.quantifier = Quantifier.OPTIONAL }
fun RulePattern.any() = this.also { it.quantifier = Quantifier.ANY }
fun RulePattern.multiple() = this.also { it.quantifier = Quantifier.MULTIPLE }

infix fun String.or(other: String) = this.once() or other.once()
infix fun String.or(other: RulePattern) = this.once() or other
infix fun RulePattern.or(other: String) = this or other.once()

infix fun String.with(other: String) = this.once() with other.once()
infix fun String.with(other: Rule) = this.once() with RuleCallPattern(other, Quantifier.ONCE)
infix fun String.with(other: RulePattern) = this.once() with other
infix fun RulePattern.with(other: String) = this with other.once()



infix fun RulePattern.or(other: RulePattern): RulePattern {
	return if (this is MultiplePossiblePattern && other is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(*this.patterns, *other.patterns), Quantifier.ONCE)
	} else if (this is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(*this.patterns, other), Quantifier.ONCE)
	} else if (other is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(this, *other.patterns), Quantifier.ONCE)
	} else {
		MultiplePossiblePattern(arrayOf(this, other), Quantifier.ONCE)
	}
}

infix fun RulePattern.with(other: RulePattern): RulePattern {
	return if (this is ChainedPattern && other is ChainedPattern) {
		ChainedPattern(arrayOf(*this.patterns, *other.patterns), Quantifier.ONCE)
	} else if (this is ChainedPattern) {
		ChainedPattern(arrayOf(*this.patterns, other), Quantifier.ONCE)
	} else if (other is ChainedPattern) {
		ChainedPattern(arrayOf(this, *other.patterns), Quantifier.ONCE)
	} else {
		ChainedPattern(arrayOf(this, other), Quantifier.ONCE)
	}
}
