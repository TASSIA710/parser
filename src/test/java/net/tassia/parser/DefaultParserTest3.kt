package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar1
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.TokenProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultParserTest3 {

	private fun test(str: String) {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		val token = parser.parseFull(str, TestGrammar1.ROOT)
		assertEquals(str, token.raw)
	}

	@Test
	fun testSuccess1() = test("Hello World!")

	@Test
	fun testSuccess2() = test("Hello Stranger!")

	@Test(expected = ParseException::class)
	fun testFailure1() = test("Hello Friend!")

	@Test(expected = ParseException::class)
	fun testFailure2() = test("Hello World! ")

}
