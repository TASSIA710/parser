package net.tassia.parser.token;

public class Tokens extends TokenType {

	private final TokenType[] tokens;

	public Tokens(TokenType[] tokens) {
		this.tokens = tokens;
	}

	public TokenType[] getTokens() {
		return tokens;
	}

}
