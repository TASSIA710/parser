package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar1
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.TokenProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenTest1 {

	@Test
	fun testSuccess1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("Hello World!", TestGrammar1.ROOT)
		require(result is TestGrammar1.RootToken)
		assertEquals("world", result.target.value)
	}

	@Test
	fun testSuccess2() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("Hello Stranger!", TestGrammar1.ROOT)
		require(result is TestGrammar1.RootToken)
		assertEquals("stranger", result.target.value)
	}

	@Test(expected = ParseException::class)
	fun testFailure1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parse("Hello Friend!", TestGrammar1.ROOT)
	}

}
