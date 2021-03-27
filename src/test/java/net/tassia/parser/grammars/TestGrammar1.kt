package net.tassia.parser.grammars

import net.tassia.parser.TokenTest1
import net.tassia.parser.kotlin.or
import net.tassia.parser.kotlin.with
import net.tassia.parser.token.MultipleTokens
import net.tassia.parser.token.StringToken
import net.tassia.parser.token.TokenType

object TestGrammar1 {

	val TARGET	by "World" or "Stranger" token
	{ token ->
		require(token is StringToken)
		if (token.value == "World") {
			return@token TargetToken.WORLD
		} else if (token.value == "Stranger") {
			return@token TargetToken.STRANGER
		}
		return@token token
	}

	val ROOT	by "Hello " with TARGET with "!" token
	{ token ->
		require(token is MultipleTokens)
		return@token RootToken(token.tokens[1] as TargetToken)
	}



	class RootToken(val target: TargetToken) : TokenType()

	class TargetToken(val value: String) : TokenType() {
		companion object {
			val WORLD = TargetToken("world")
			val STRANGER = TargetToken("stranger")
		}
	}

}
