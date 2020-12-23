package net.tassia.parser;

import net.tassia.parser.rule.Quantifier;
import net.tassia.parser.rule.Rule;
import net.tassia.parser.rule.RulePattern;
import net.tassia.parser.rule.RuleSet;
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
	public void testBasicParser() throws IOException, ParseException {
		var inputBytes = readResource("test_input_1.txt");
		var input = new String(inputBytes, StandardCharsets.UTF_8).trim();

		var rules = new RuleSet();
		rules.setRoot(new Rule("ROOT", new RulePattern.ConstantString(Quantifier.MULTIPLE, "ab")));

		var parser = new DefaultParser();
		try {
			parser.parse(rules, input);
		} catch (ParseException ex) {
			System.out.println(ParseException.getDisplayed(ex));
			System.out.println("-".repeat(30) + "\n");
			ex.printStackTrace();
		}
	}

}
