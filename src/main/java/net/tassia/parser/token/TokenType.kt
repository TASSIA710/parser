package net.tassia.parser.token

open class TokenType

object NoneToken : TokenType()

class MultipleTokens(val tokens: List<TokenType>) : TokenType()

class StringToken(val value: String) : TokenType()
