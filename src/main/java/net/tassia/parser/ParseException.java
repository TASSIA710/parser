package net.tassia.parser;

/**
 * Thrown when something goes wrong when parsing.
 * If possible, the source string, the position and length of the error are included in this exception.
 * @since Parser 1.0
 * @author Tassilo
 */
public class ParseException extends Exception {

	private final String source;
	private final int position, length;

	/**
	 * Creates a new generic {@link ParseException} that doesn't have any clear source.
	 * @param message the message
	 */
	public ParseException(String message) {
		this(message, null, -1, 0);
	}

	/**
	 * Creates a new {@link ParseException} with the given source and position.
	 * @param message the message
	 * @param source the source string
	 * @param position the position of the error
	 * @param length the length of the error
	 */
	public ParseException(String message, String source, int position, int length) {
		super(message);
		this.source = source;
		this.position = position;
		this.length = length;
	}

	/**
	 * Returns the source string of this error. Can be <code>null</code>.
	 * @return the source string
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Returns the position of the error in the source string. Will be
	 * <code>-1</code> if {@link #getSource()} is <code>null</code>.
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Returns the length of the error in the source string. Will be
	 * <code>0</code> if {@link #getSource()} is <code>null</code>.
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Generates an informative display of the error by checking the source, error position and length.
	 * For example:<br/>
	 * <pre>{@code
	 * var ex = new ParseException("Unexpected '@'", "Hell@o World!", 4, 1);
	 * System.out.println(ParseException.getDisplayed(ex));
	 * }</pre>
	 * Would render to:
	 * <pre>
	 * Hell@o World!
	 *     ^
	 * </pre>
	 * @param ex the message to format
	 * @return a displayable string
	 */
	public static String getDisplayed(ParseException ex) {
		var str = ex.source;
		str += "\n";
		str += " ".repeat(ex.position) + "^".repeat(ex.length);
		return str;
	}

}
