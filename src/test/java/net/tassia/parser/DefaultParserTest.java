package net.tassia.parser;

import net.tassia.parser.rule.RuleSet;
import net.tassia.parser.token.TokenProvider;
import net.tassia.parser.token.TokenType;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DefaultParserTest {

	private byte[] readResource(String path) throws IOException {
		var in = getClass().getClassLoader().getResourceAsStream(path);
		if (in == null) throw new IOException("Resource '" + path + "' not found.");
		return in.readAllBytes();
	}

	@Test
	public void testBasicParser() throws Exception {
		var inputBytes = readResource("test_bnf_1.bnf");
		var input = new String(inputBytes, StandardCharsets.UTF_8).trim();

		RuleSet rules;
		var bnfParser = new DefaultBNFParser();
		try {
			rules = bnfParser.parse(input);
		} catch (ParseException ex) {
			System.out.println(ParseException.getDisplayed(ex));
			throw ex;
		}

		var parser = new DefaultParser(TokenProvider.NO_OPERATION, rules);

		parser.parse("Hello World!");

		parser.parse("Hello Stranger!");

		try {
			parser.parse("Hello Friend!");
			// This didn't error, so manually throw an exception
			throw new Exception();
		} catch (ParseException ignored) {
			// This is supposed to error
		}
	}

}
