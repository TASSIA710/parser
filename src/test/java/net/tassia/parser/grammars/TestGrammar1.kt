package net.tassia.parser.grammars

import net.tassia.parser.kotlin.or
import net.tassia.parser.kotlin.with
import net.tassia.parser.token.MultipleTokens
import net.tassia.parser.token.StringToken
import net.tassia.parser.token.TokenType

object TestGrammar1 {

	// <TARGET> ::= "World" | "Stranger"
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

	// <ROOT> ::= "Hello " <TARGET> "!"
	val ROOT	by "Hello " with TARGET with "!" token
	{ token ->
		require(token is MultipleTokens)
		return@token RootToken(token.tokens[1] as TargetToken, token.raw)
	}



	class RootToken(val target: TargetToken, raw: String) : TokenType(raw)

	class TargetToken(val value: String, raw: String) : TokenType(raw) {
		companion object {
			val WORLD = TargetToken("world", "World")
			val STRANGER = TargetToken("stranger", "Stranger")
		}
	}

}
