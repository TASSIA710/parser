package net.tassia.parser;

import net.tassia.parser.rule.RuleSet;

/**
 * A parser is responsible for using a given {@link RuleSet} to parse a {@link String}.
 * @since Parser 1.0
 * @author Tassilo
 */
public abstract class Parser {

	/**
	 * Parses the given source string using the given {@link RuleSet}.
	 * @param rules the rules
	 * @param source the source string
	 * @throws ParseException if, for example, an unexpected token was encountered
	 */
	public abstract void parse(RuleSet rules, String source) throws ParseException;

}
