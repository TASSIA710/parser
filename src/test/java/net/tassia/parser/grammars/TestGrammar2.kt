package net.tassia.parser.grammars

import net.tassia.parser.kotlin.*
import net.tassia.parser.token.MultipleTokens
import net.tassia.parser.token.StringToken
import net.tassia.parser.token.TokenType

object TestGrammar2 {

	// <DIGIT> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
	val DIGIT	by where(Char::isDigit)



	// <INTEGER> ::= <DIGIT>+
	val INTEGER	by DIGIT.multiple() token { token ->
		require(token is MultipleTokens)
		val str = token.tokens.joinToString(separator = "") {
			require(it is StringToken)
			return@joinToString it.value
		}
		return@token IntegerToken(str.toLong().toDouble(), token.raw)
	}



	// <PRODUCT> ::= <NUMBER> (("*" | "/") <NUMBER>)*
	val PRODUCT by INTEGER with (("*" or "/") with INTEGER).any() token { token ->
		require(token is MultipleTokens)
		var temp = token.tokens[0].let {
			require(it is IntegerToken)
			return@let it.value
		}
		token.tokens[1].let {
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
		return@token ProductToken(temp, token.raw)
	}



	// <SUM> ::= <PRODUCT> (("+" | "-") <PRODUCT>)*
	val SUM		by PRODUCT with (("+" or "-") with PRODUCT).any() token { token ->
		require(token is MultipleTokens)
		var temp = token.tokens[0].let {
			require(it is ProductToken)
			return@let it.value
		}
		token.tokens[1].let {
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
		return@token SumToken(temp, token.raw)
	}



	// <TERM> ::= <SUM>
	val TERM	by SUM token { token ->
		require(token is SumToken)
		return@token TermToken(token.value, token.raw)
	}



	class TermToken(val value: Double, raw: String) : TokenType(raw)

	class SumToken(val value: Double, raw: String) : TokenType(raw)

	class ProductToken(val value: Double, raw: String) : TokenType(raw)

	class IntegerToken(val value: Double, raw: String) : TokenType(raw)

}
