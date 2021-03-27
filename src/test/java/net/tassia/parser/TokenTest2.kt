package net.tassia.parser

import net.tassia.parser.grammars.TestGrammar2
import net.tassia.parser.impl.DefaultParser
import net.tassia.parser.token.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TokenTest2 {

	@Test
	fun test1() {
		val parser = DefaultParser(provider)
		val result = parser.parse("2+2", TestGrammar2.TERM)
		require(result is TermToken)
		assertEquals(4.0, result.value)
	}



	private val provider = TokenProvider { rule, raw ->
		when (rule.name) {
			"TERM" -> {
				require(raw is SumToken)
				return@TokenProvider TermToken(raw.value)
			}
			"SUM" -> {
				require(raw is MultipleTokens)
				var temp = raw.tokens[0].let {
					require(it is ProductToken)
					return@let it.value
				}
				raw.tokens[1].let {
					require(it is MultipleTokens)
					it.tokens.forEach { product ->
						require(product is MultipleTokens)
						val op = product.tokens[0].run {
							require(this is StringToken)
							return@run this.value
						}
						val value = product.tokens[1].run {
							require(this is ProductToken)
							return@run this.value
						}
						when (op) {
							"+" -> temp += value
							"-" -> temp -= value
							else -> throw IllegalStateException("Unknown sum-operator: $op")
						}
					}
				}
				return@TokenProvider SumToken(temp)
			}
			"PRODUCT" -> {
				require(raw is MultipleTokens)
				var temp = raw.tokens[0].let {
					require(it is IntegerToken)
					return@let it.value
				}
				raw.tokens[1].let {
					require(it is MultipleTokens)
					it.tokens.forEach { factor ->
						require(factor is MultipleTokens)
						val op = factor.tokens[0].run {
							require(this is StringToken)
							return@run this.value
						}
						val value = factor.tokens[1].run {
							require(this is IntegerToken)
							return@run this.value
						}
						when (op) {
							"*" -> temp *= value
							"/" -> temp /= value
							else -> throw IllegalStateException("Unknown product-operator: $op")
						}
					}
				}
				return@TokenProvider ProductToken(temp)
			}
			"INTEGER" -> {
				require(raw is MultipleTokens)
				val str = raw.tokens.joinToString(separator = "") {
					require(it is StringToken)
					return@joinToString it.value
				}
				return@TokenProvider IntegerToken(str.toLong().toDouble())
			}
		}
		return@TokenProvider raw
	}

	class TermToken(val value: Double) : TokenType()
	class SumToken(val value: Double) : TokenType()
	class ProductToken(val value: Double) : TokenType()
	class IntegerToken(val value: Double) : TokenType()

}
