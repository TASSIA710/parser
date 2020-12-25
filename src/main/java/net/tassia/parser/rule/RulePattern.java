package net.tassia.parser.rule;

public abstract class RulePattern {

	private Quantifier quantifier;

	private RulePattern(Quantifier quantifier) {
		this.quantifier = quantifier;
	}

	public Quantifier getQuantifier() {
		return quantifier;
	}

	public void setQuantifier(Quantifier quantifier) {
		this.quantifier = quantifier;
	}

	public abstract String toString();



	public static class Chained extends RulePattern {

		private final RulePattern[] patterns;

		public Chained(Quantifier quantifier, RulePattern...patterns) {
			super(quantifier);
			this.patterns = patterns;
		}

		public RulePattern[] getPatterns() {
			return patterns;
		}

		@Override
		public String toString() {
			var sb = new StringBuilder("(");
			for (var pattern : patterns) {
				sb.append(pattern.toString());
				sb.append(" ");
			}
			var str = sb.toString();
			return str.substring(0, str.length() - 1) + ")";
		}

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

		@Override
		public String toString() {
			var sb = new StringBuilder();
			for (var pattern : patterns) {
				sb.append(pattern.toString());
				sb.append(" | ");
			}
			var str = sb.toString();
			return str.substring(0, str.length() - 3);
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

		@Override
		public String toString() {
			return "<" + rule + ">";
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

		@Override
		public String toString() {
			// TODO: Escape value
			return "\"" + value + "\"";
		}

	}

}
