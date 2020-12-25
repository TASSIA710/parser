package net.tassia.parser.token;

public abstract class TokenType {

	private TokenType() {
	}



	public static class Multiple extends TokenType {

		private final TokenType[] tokens;

		public Multiple(TokenType[] tokens) {
			this.tokens = tokens;
		}

		public TokenType[] getTokens() {
			return tokens;
		}

	}



	public static class StringValue extends TokenType {

		private final String value;

		public StringValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
