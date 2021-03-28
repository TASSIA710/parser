package net.tassia.parser

class EOFException(source: String) : ParseException(source, -1, 0, "The end of the source string has been reached.")
