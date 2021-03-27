package net.tassia.parser.grammars

import net.tassia.parser.kotlin.*

object TestGrammar2 {

	// <DIGIT> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
	val DIGIT	by "0" or "1" or "2" or "3" or "4" or "5" or "6" or "7" or "8" or "9"

	// <INTEGER> ::= <DIGIT>+
	val INTEGER	by DIGIT.multiple()

	// <PRODUCT> ::= <NUMBER> (("*" | "/") <NUMBER>)*
	val PRODUCT by INTEGER with (("*" or "/") with INTEGER).any()

	// <SUM> ::= <PRODUCT> (("+" | "-") <PRODUCT>)*
	val SUM		by PRODUCT with (("+" or "-") with PRODUCT).any()

	// <TERM> ::= <SUM>
	val TERM	by SUM

}
