package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar1
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.TokenProvider
import kotlin.test.Test

class DefaultParserTest2 {

	@Test
	fun testSuccess1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parseFull("Hello World!", TestGrammar1.ROOT)
	}

	@Test
	fun testSuccess2() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parseFull("Hello Stranger!", TestGrammar1.ROOT)
	}

	@Test(expected = ParseException::class)
	fun testFailure1() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parseFull("Hello Friend!", TestGrammar1.ROOT)
	}

	@Test(expected = ParseException::class)
	fun testFailure2() {
		val parser = DefaultParser(TokenProvider.NO_OPERATION)
		parser.parseFull("Hello World! ", TestGrammar1.ROOT)
	}

}
