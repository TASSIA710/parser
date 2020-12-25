package net.tassia.parser.rule;

/**
 * Defines a rule (basically a single line of a BNF file).
 * @since Parser 1.0
 * @author Tassilo
 */
public class Rule {

	private final String name;
	private final RulePattern pattern;

	/**
	 * Creates a new rule with the given name and pattern.
	 * @param name the name
	 * @param pattern the pattern
	 */
	public Rule(String name, RulePattern pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	/**
	 * Returns the name of this rule.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the pattern of this rule.
	 * @return the pattern
	 */
	public RulePattern getPattern() {
		return pattern;
	}

}
