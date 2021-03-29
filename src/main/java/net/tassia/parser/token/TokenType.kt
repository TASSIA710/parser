package net.tassia.parser.token

abstract class TokenType(val raw: String)

object NoneToken : TokenType("")

class MultipleTokens(val tokens: List<TokenType>)
	: TokenType(tokens.joinToString(separator = "") { it.raw })

class StringToken(val value: String) : TokenType(value)
