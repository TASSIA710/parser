package net.tassia.parser.rule;

public abstract class RulePattern {

	private final Quantifier quantifier;

	private RulePattern(Quantifier quantifier) {
		this.quantifier = quantifier;
	}

	public Quantifier getQuantifier() {
		return quantifier;
	}



	public static class MultiplePossible extends RulePattern {

		private final RulePattern[] patterns;

		public MultiplePossible(Quantifier quantifier, RulePattern...patterns) {
			super(quantifier);
			this.patterns = patterns;
		}

		public RulePattern[] getPatterns() {
			return patterns;
		}

	}



	public static class RuleCall extends RulePattern {

		private final String rule;

		public RuleCall(Quantifier quantifier, String rule) {
			super(quantifier);
			this.rule = rule;
		}

		public String getRule() {
			return rule;
		}

	}



	public static class ConstantString extends RulePattern {

		private final String value;

		public ConstantString(Quantifier quantifier, String value) {
			super(quantifier);
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
