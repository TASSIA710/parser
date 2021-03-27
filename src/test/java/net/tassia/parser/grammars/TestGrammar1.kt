package net.tassia.parser.grammars

import net.tassia.parser.kotlin.or
import net.tassia.parser.kotlin.with

object TestGrammar1 {

	val TARGET	by "World" or "Stranger"
	val ROOT	by "Hello " with TARGET with "!"

}
