package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar1
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.TokenProvider
import kotlin.test.Test

class DefaultParserTest1 {

	@Test
	fun testSuccess1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parse("Hello World!", TestGrammar1.ROOT)
	}

	@Test
	fun testSuccess2() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parse("Hello Stranger!", TestGrammar1.ROOT)
	}

	@Test(expected = ParseException::class)
	fun testFailure1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parse("Hello Friend!", TestGrammar1.ROOT)
	}

}
