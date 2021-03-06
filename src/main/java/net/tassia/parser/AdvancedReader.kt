package net.tassia.parser

import java.util.*

open class AdvancedReader(val source: String) {

	private var index: Int = 0
	private val indexStack: Stack<Int> = Stack()



	fun isAtEOF() = index >= source.length



	fun peek(): Char {
		if (index < source.length) {
			return source[index]
		} else {
			throw EOFException(source)
		}
	}

	fun peek(len: Int): String {
		if (index + len <= source.length) {
			return source.substring(index, index + len)
		} else {
			throw EOFException(source)
		}
	}

	fun peekSafe(len: Int): String {
		return if (index + len <= source.length) {
			source.substring(index, index + len)
		} else {
			source.substring(index) + "<EOF>"
		}
	}

	fun next(): Char = peek().also { index++ }
	fun next(len: Int) = peek(len).also { index += len }

	fun isPeek(test: Char): Boolean = index < source.length && peek() == test
	fun isPeek(test: String): Boolean = index + test.length <= source.length && peek(test.length) == test

	fun isNext(test: Char): Boolean = isPeek(test).also { if (it) next() }
	fun isNext(test: String): Boolean = isPeek(test).also { if (it) next(test.length) }



	fun nextWhile(predicate: (Char) -> Boolean): String {
		val builder = StringBuilder()
		while (predicate(peek())) {
			builder.append(next())
		}
		return builder.toString()
	}

	fun nextWhileNot(predicate: (Char) -> Boolean): String = nextWhileNot { !predicate(it) }



	fun pushReset() = indexStack.push(index)!!

	fun clearReset() = indexStack.pop()!!

	fun reset() = indexStack.pop()!!.also { index = it }

	fun clearResetAll() = indexStack.clear()



	fun expected(vararg expected: String): ParseException {
		val maxPeek = expected.maxOf { it.length }
		return unexpected(peek(maxPeek), *expected)
	}

	fun unexpected(got: String, vararg expected: String): ParseException {
		val msg = "Unexpected '$got', was expecting '${expected.joinToString()}'."
		return ParseException(source, index, 1.coerceAtLeast(got.length), msg)
	}

}
