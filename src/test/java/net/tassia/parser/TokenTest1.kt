package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar1
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.MultipleTokens
import net.tassia.parser.token.StringToken
import net.tassia.parser.token.TokenProvider
import net.tassia.parser.token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenTest1 {

	@Test
	fun testSuccess1() {
		val parser = DefaultParser(provider)
		val result = parser.parse("Hello World!", TestGrammar1.ROOT)
		require(result is RootToken)
		assertEquals("world", result.target.toString())
	}

	@Test
	fun testSuccess2() {
		val parser = DefaultParser(provider)
		val result = parser.parse("Hello Stranger!", TestGrammar1.ROOT)
		require(result is RootToken)
		assertEquals("stranger", result.target.toString())
	}

	@Test(expected = ParseException::class)
	fun testFailure1() {
		val parser = DefaultParser(provider)
		parser.parse("Hello Friend!", TestGrammar1.ROOT)
	}



	private val provider = TokenProvider { rule, raw ->
		if (rule.name == "ROOT") {
			// Structure:
			// [0] = "Hello "
			// [1] = TargetToken
			// [2] = "!"
			require(raw is MultipleTokens)
			return@TokenProvider RootToken(raw.tokens[1] as TargetToken)

		} else if (rule.name == "TARGET") {
			require(raw is StringToken)
			if (raw.value == "World") {
				return@TokenProvider TargetToken.WORLD
			} else if (raw.value == "Stranger") {
				return@TokenProvider TargetToken.STRANGER
			}

		}
		return@TokenProvider raw
	}

	class RootToken(val target: TargetToken) : TokenType()

	class TargetToken(private val value: String) : TokenType() {
		override fun toString() = value
		companion object {
			val WORLD = TargetToken("world")
			val STRANGER = TargetToken("stranger")
		}
	}

}
