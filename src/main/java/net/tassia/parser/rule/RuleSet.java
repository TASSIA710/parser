package net.tassia.parser.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * A rule set is a set of rules (basically an entire BNF file). It also has a 'root' rule,
 * which acts as the entrypoint for the parser.
 * @since Parser 1.0
 * @author Tassilo
 */
public class RuleSet {

	private Rule root;
	private final Map<String, Rule> rules;

	/**
	 * Creates a new empty rule set.
	 */
	public RuleSet() {
		this.root = null;
		this.rules = new HashMap<>();
	}

	/**
	 * Returns the root rule of this rule set. Although for non-empty rule sets this should never be <code>null</code>,
	 * this can still return <code>null</code> even for non-empty rule sets.
	 * @return the root rule, or <code>null</code> if not set
	 */
	public Rule getRoot() {
		return root;
	}

	/**
	 * Sets the root rule for this rule set.
	 * @param root the root rule
	 */
	public void setRoot(Rule root) {
		this.root = root;
	}

	/**
	 * Adds a new rule to this rule set.
	 * @param rule the rule to add
	 */
	public void addRule(Rule rule) {
		rules.put(rule.getName(), rule);
	}

	/**
	 * Tries to get a rule by it's {@link Rule#getName()}.
	 * @param name the name
	 * @return the found rule, or <code>null</code>
	 */
	public Rule getRule(String name) {
		return rules.get(name);
	}

}
