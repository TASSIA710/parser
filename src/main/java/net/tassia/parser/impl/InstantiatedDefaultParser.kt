package net.tassia.parser.impl

import net.tassia.parser.AdvancedReader
import net.tassia.parser.ParseException
import net.tassia.parser.rule.*
import net.tassia.parser.token.*

class InstantiatedDefaultParser(private val provider: TokenProvider, source: String) : AdvancedReader(source) {

	@Throws(ParseException::class)
	fun readPattern(pattern: RulePattern): TokenType {
		return when (pattern.quantifier) {
			Quantifier.ONCE -> readPatternOnce(pattern)
			Quantifier.OPTIONAL -> readPatternOptional(pattern)
			Quantifier.MULTIPLE -> readPatternMultiple(pattern)
			Quantifier.ANY -> readPatternAny(pattern)
		}
	}



	@Throws(ParseException::class)
	fun readPatternOnce(pattern: RulePattern): TokenType {
		// Read the pattern exactly once
		return when (pattern) {
			is ConstantStringPattern -> readConstantString(pattern)
			is CharMatchPattern -> readCharMatch(pattern)
			is RuleCallPattern -> readRuleCall(pattern)
			is MultiplePossiblePattern -> readMultiplePossible(pattern)
			is ChainedPattern -> readChained(pattern)
		}
	}

	@Throws(ParseException::class)
	fun readPatternOptional(pattern: RulePattern): TokenType {
		// Read the pattern exactly once
		// If it fails, continue with null
		pushReset()
		try {
			val token = readPatternOnce(pattern)
			clearReset()
			return token
		} catch (ex: ParseException) {
			reset()
			return NoneToken
		}
	}

	@Throws(ParseException::class)
	fun readPatternMultiple(pattern: RulePattern): TokenType {
		// Read the pattern at least one time
		val tokens = mutableListOf<TokenType>()
		tokens.add(readPatternOnce(pattern))
		while (true) {
			pushReset()
			try {
				tokens.add(readPatternOnce(pattern))
				clearReset()
			} catch (ex: ParseException) {
				reset()
				break
			}
		}
		return MultipleTokens(tokens)
	}

	@Throws(ParseException::class)
	fun readPatternAny(pattern: RulePattern): TokenType {
		// Read the pattern as often as possible
		val tokens = mutableListOf<TokenType>()
		while (true) {
			pushReset()
			try {
				tokens.add(readPatternOnce(pattern))
				clearReset()
			} catch (ex: ParseException) {
				reset()
				break
			}
		}
		return MultipleTokens(tokens)
	}



	@Throws(ParseException::class)
	fun readConstantString(pattern: ConstantStringPattern): TokenType {
		if (isNext(pattern.value)) {
			return StringToken(pattern.value)
		} else {
			throw unexpected(peekSafe(pattern.value.length), pattern.value)
		}
	}

	@Throws(ParseException::class)
	fun readCharMatch(pattern: CharMatchPattern): TokenType {
		if (pattern.value(peek())) {
			return StringToken(next().toString())
		} else {
			throw unexpected(peek().toString(), pattern.toString())
		}
	}

	@Throws(ParseException::class)
	fun readRuleCall(pattern: RuleCallPattern): TokenType {
		try {
			var token = readPattern(pattern.rule.pattern)
			token = pattern.rule.tokenProvider.provide(pattern.rule, token)
			return provider.provide(pattern.rule, token)
		} catch (ex: ParseException) {
			throw ParseException(ex.source, ex.position, ex.length, "Failed to parse rule " + pattern.rule.name, ex)
		}
	}

	@Throws(ParseException::class)
	fun readMultiplePossible(pattern: MultiplePossiblePattern): TokenType {
		for (p in pattern.patterns) {
			pushReset()
			try {
				val result = readPattern(p)
				clearReset()
				return result
			} catch (ex: ParseException) {
				reset()
			}
		}
		throw expected(pattern.toString())
	}

	@Throws(ParseException::class)
	fun readChained(pattern: ChainedPattern): TokenType {
		val tokens = mutableListOf<TokenType>()
		for (p in pattern.patterns) {
			tokens.add(readPattern(p))
		}
		return MultipleTokens(tokens)
	}

}
