package net.tassia.parser;

import net.tassia.parser.rule.RuleSet;
import net.tassia.parser.token.TokenProvider;
import net.tassia.parser.token.TokenType;
import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;

public class HelloWorldTest {

	private byte[] readResource(String path) throws IOException {
		var in = getClass().getClassLoader().getResourceAsStream(path);
		if (in == null) throw new IOException("Resource '" + path + "' not found.");
		return in.readAllBytes();
	}

	@Test
	public void testBasicParser() throws Exception {
		var inputBytes = readResource("test_hello_world.bnf");
		var input = new String(inputBytes, StandardCharsets.UTF_8).trim();

		RuleSet rules;
		var bnfParser = new DefaultBNFParser();
		try {
			rules = bnfParser.parse(input);
		} catch (ParseException ex) {
			System.out.println(ParseException.getDisplayed(ex));
			throw ex;
		}

		var parser = new DefaultParser((rule, raw) -> {
			if (rule.getName().equals("ROOT")) {
				var cast = (TokenType.Multiple) raw;
				// Structure:
				// [0] = "Hello "
				// [1] = TargetToken
				// [2] = "!"
				return new RootToken((TargetToken) cast.getTokens()[1]);

			} else if (rule.getName().equals("TARGET")) {
				var cast = (TokenType.StringValue) raw;
				if (cast.getValue().equals("World")) {
					return TargetToken.WORLD;
				} else if (cast.getValue().equals("Stranger")) {
					return TargetToken.STRANGER;
				}

			}
			return null;
		}, rules);

		var raw1 = parser.parse("Hello World!");
		var cast1 = (RootToken) raw1;
		System.out.println(cast1.target.toString());

		var raw2 = parser.parse("Hello Stranger!");
		var cast2 = (RootToken) raw2;
		System.out.println(cast2.target.toString());

		try {
			parser.parse("Hello Friend!");
			// This didn't error, so manually throw an exception
			throw new Exception();
		} catch (ParseException ignored) {
			// This is supposed to error
		}
	}

	private static class RootToken extends TokenType {
		public final TargetToken target;
		public RootToken(TargetToken target) {
			this.target = target;
		}
	}

	private static class TargetToken extends TokenType {
		public static final TargetToken WORLD = new TargetToken("world");
		public static final TargetToken STRANGER = new TargetToken("stranger");
		private final String str;
		private TargetToken(String str) {
			this.str = str;
		}
		@Override
		public String toString() {
			return str;
		}
	}

}
