package net.tassia.parser.rule;

/**
 * Defines how many times a given pattern should be matched.
 * @since Parser 1.0
 * @author Tassilo
 */
public enum Quantifier {

	/**
	 * The given pattern must be matched once.
	 */
	ONCE,

	/**
	 * The given pattern can be matched once.
	 */
	OPTIONAL,

	/**
	 * The given pattern must be matched at least once.
	 */
	MULTIPLE,

	/**
	 * The given pattern can be matched an undefined amount of times.
	 */
	ANY

}
