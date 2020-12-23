package net.tassia.parser.rule;

import java.util.HashMap;
import java.util.Map;

public class RuleSet {

	private Rule root;
	private final Map<String, Rule> rules;

	public RuleSet() {
		this.root = null;
		this.rules = new HashMap<>();
	}

	public Rule getRoot() {
		return root;
	}

	public void setRoot(Rule root) {
		this.root = root;
	}

	public void addRule(Rule rule) {
		rules.put(rule.getName(), rule);
	}

	public Rule getRule(String name) {
		return rules.get(name);
	}

}
