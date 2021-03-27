package net.tassia.parser.rule

/**
 * Defines a rule (basically a single line of a BNF file).
 *
 * @param name the name of the rule
 * @param pattern the pattern of the rule
 *
 * @since Parser 1.0
 * @author Tassilo
 */
data class Rule(

	/**
	 * The name of this rule.
	 */
	val name: String,


	/**
	 * The pattern of this rule.
	 */
	val pattern: RulePattern,

)
