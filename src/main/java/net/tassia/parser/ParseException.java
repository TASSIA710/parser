package net.tassia.parser;

public class ParseException extends Exception {

	private final String source;
	private final int position, length;

	public ParseException(String message) {
		this(message, null, -1, 0);
	}

	public ParseException(String message, String source, int position, int length) {
		super(message);
		this.source = source;
		this.position = position;
		this.length = length;
	}

	public String getSource() {
		return source;
	}

	public int getPosition() {
		return position;
	}

	public int getLength() {
		return length;
	}

	public static String getDisplayed(ParseException ex) {
		var sb = new StringBuilder();

		sb.append(ex.source);

		sb.append("\n");
		sb.append(" ".repeat(ex.position));
		sb.append("^".repeat(ex.length));

		return sb.toString();
	}

}
