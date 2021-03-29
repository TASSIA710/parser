package net.tassia.parser.token

abstract class TokenType {

	abstract fun rawString(): String

}

object NoneToken : TokenType() {
	override fun rawString(): String {
		return ""
	}
}

class MultipleTokens(val tokens: List<TokenType>) : TokenType() {
	override fun rawString(): String {
		return tokens.joinToString(separator = "", transform = TokenType::rawString)
	}
}

class StringToken(val value: String) : TokenType() {
	override fun rawString(): String {
		return value
	}
}
