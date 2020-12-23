package net.tassia.parser.rule;

public class Rule {

	private final String name;
	private final RulePattern pattern;

	public Rule(String name, RulePattern pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	public String getName() {
		return name;
	}

	public RulePattern getPattern() {
		return pattern;
	}

}
