package net.tassia.parser.rule

/**
 * Defines how many times a given pattern should be matched.
 *
 * @since Parser 1.0
 * @author Tassilo
 */
enum class Quantifier {

	/**
	 * The given pattern must be matched exactly once.
	 */
	ONCE,

	/**
	 * The given pattern can be matched once, or not at all.
	 */
	OPTIONAL,

	/**
	 * The given pattern must be matched at least once.
	 */
	MULTIPLE,

	/**
	 * The given pattern can be matched an undefined amount of times.
	 */
	ANY;

}
