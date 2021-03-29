package net.tassia.parser.impl

import net.tassia.parser.Parser
import net.tassia.parser.rule.Quantifier
import net.tassia.parser.rule.Rule
import net.tassia.parser.rule.RuleCallPattern
import net.tassia.parser.token.TokenProvider
import net.tassia.parser.token.TokenType

class DefaultParser(provider: TokenProvider) : Parser(provider) {

	override fun parse(source: String, rule: Rule, offset: Int): TokenType {
		val parser = InstantiatedDefaultParser(provider, source.substring(offset))
		return parser.readPattern(RuleCallPattern(rule, Quantifier.ONCE))
	}

	override fun parseFull(source: String, rule: Rule, offset: Int): TokenType {
		val parser = InstantiatedDefaultParser(provider, source.substring(offset))
		val token = parser.readPattern(RuleCallPattern(rule, Quantifier.ONCE))
		if (parser.isAtEOF()) return token
		throw parser.expected("<EOF>")
	}

}
