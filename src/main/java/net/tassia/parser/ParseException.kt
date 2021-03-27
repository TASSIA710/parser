package net.tassia.parser

import java.io.PrintWriter
import java.io.StringWriter

/**
 * Thrown when something goes wrong when parsing.
 * If possible, the source string, the position and length
 * of the error will be included in this exception.
 *
 * @param source the source string
 * @param position the error position
 * @param length the error length
 * @param message the message
 * @param cause the cause
 *
 * @since Parser 1.0
 * @author Tassilo
 */
class ParseException(

	/**
	 * The source string of this error.
	 */
	val source: String?,

	/**
	 * The position of the error in the source string.
	 */
	val position: Int,

	/**
	 * The length of the error in the source string.
	 */
	val length: Int,

	/**
	 * The message of the error.
	 */
	message: String? = null,

	/**
	 * The cause of the error.
	 */
	cause: Throwable? = null,

) : Exception(message, cause) {

	/**
	 * Generates an informative display of the error by checking the source, error position and length.
	 *
	 * @return a displayable string
	 */
	fun display(): String {
		var str = source ?: message ?: StringWriter().let {
			printStackTrace(PrintWriter(it))
			return it.toString()
		}

		str += "\n"
		str += " ".repeat(position) + "^".repeat(length)
		return str
	}

}
