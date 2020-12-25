package net.tassia.parser;

import net.tassia.parser.rule.RuleSet;

/**
 * A BNF parser is responsible for taking in a BNF file (as a string) and parsing it into a {@link RuleSet}.
 * @since Parser 1.0
 * @author Tassilo
 */
public abstract class BNFParser {

	/**
	 * Parses the given input string into a {@link RuleSet}.
	 * @param input the input string
	 * @return the parsed rules
	 * @throws ParseException if the input string has invalid sytnax
	 */
	public abstract RuleSet parse(String input) throws ParseException;

}
