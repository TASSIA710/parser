package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar2
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenTest2 {

	@Test
	fun test1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("2+2", TestGrammar2.TERM)
		require(result is TestGrammar2.TermToken)
		assertEquals(4.0, result.value)
	}

	@Test
	fun test2() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("2*2", TestGrammar2.TERM)
		require(result is TestGrammar2.TermToken)
		assertEquals(4.0, result.value)
	}

	@Test
	fun test3() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("2+2*2", TestGrammar2.TERM)
		require(result is TestGrammar2.TermToken)
		assertEquals(6.0, result.value)
	}

	@Test
	fun test4() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val result = parser.parse("2*2+2", TestGrammar2.TERM)
		require(result is TestGrammar2.TermToken)
		assertEquals(6.0, result.value)
	}

}
