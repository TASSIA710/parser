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

fun Rule.once() = RuleCallPattern(this, Quantifier.ONCE)
fun Rule.optional() = RuleCallPattern(this, Quantifier.OPTIONAL)
fun Rule.any() = RuleCallPattern(this, Quantifier.ANY)
fun Rule.multiple() = RuleCallPattern(this, Quantifier.MULTIPLE)

infix fun String.or(other: String) = this.once() or other.once()
infix fun String.or(other: Rule) = this.once() or other.once()
infix fun String.or(other: RulePattern) = this.once() or other
infix fun Rule.or(other: String) = this.once() or other.once()
infix fun Rule.or(other: Rule) = this.once() or other.once()
infix fun Rule.or(other: RulePattern) = this.once() or other
infix fun RulePattern.or(other: String) = this or other.once()
infix fun RulePattern.or(other: Rule) = this or other.once()

infix fun String.with(other: String) = this.once() with other.once()
infix fun String.with(other: Rule) = this.once() with other.once()
infix fun String.with(other: RulePattern) = this.once() with other
infix fun Rule.with(other: String) = this.once() with other.once()
infix fun Rule.with(other: Rule) = this.once() with other.once()
infix fun Rule.with(other: RulePattern) = this.once() with other
infix fun RulePattern.with(other: String) = this with other.once()
infix fun RulePattern.with(other: Rule) = this with other.once()



operator fun CharMatchPattern.not(): CharMatchPattern {
	return CharMatchPattern({ !value(it) }, quantifier)
}



fun range(range: CharRange): RulePattern {
	return MultiplePossiblePattern(range.map {
		ConstantStringPattern(it.toString(), Quantifier.ONCE)
	}.toTypedArray(), Quantifier.ONCE)
}

fun where(predicate: (Char) -> Boolean): CharMatchPattern {
	return CharMatchPattern(predicate, Quantifier.ONCE)
}



infix fun RulePattern.or(other: RulePattern): RulePattern {
	/*return if (this is MultiplePossiblePattern && other is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(*this.patterns, *other.patterns), Quantifier.ONCE)
	} else if (this is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(*this.patterns, other), Quantifier.ONCE)
	} else if (other is MultiplePossiblePattern) {
		MultiplePossiblePattern(arrayOf(this, *other.patterns), Quantifier.ONCE)
	} else {
		MultiplePossiblePattern(arrayOf(this, other), Quantifier.ONCE)
	}*/
	return MultiplePossiblePattern(arrayOf(this, other), Quantifier.ONCE)
}

infix fun RulePattern.with(other: RulePattern): RulePattern {
	if (this is ChainedPattern && other is ChainedPattern && this.quantifier == Quantifier.ONCE && other.quantifier == Quantifier.ONCE) {
		return ChainedPattern(arrayOf(*this.patterns, *other.patterns), Quantifier.ONCE)
	} else if (this is ChainedPattern && this.quantifier == Quantifier.ONCE) {
		return ChainedPattern(arrayOf(*this.patterns, other), Quantifier.ONCE)
	} else if (other is ChainedPattern && other.quantifier == Quantifier.ONCE) {
		return ChainedPattern(arrayOf(this, *other.patterns), Quantifier.ONCE)
	}
	return ChainedPattern(arrayOf(this, other), Quantifier.ONCE)
}
