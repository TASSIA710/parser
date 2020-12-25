package net.tassia.parser.token;

public class StringToken extends TokenType {

	private final String value;

	public StringToken(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
