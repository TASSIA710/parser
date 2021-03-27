package net.tassia.parser.rule

class RuleSet {

	private val rules: MutableMap<String, Rule> = mutableMapOf()

	operator fun get(name: String): Rule? {
		return rules[name]
	}

	operator fun set(name: String, rule: Rule) {
		rules[name] = rule
	}

}
