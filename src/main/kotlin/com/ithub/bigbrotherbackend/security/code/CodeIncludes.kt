package com.ithub.bigbrotherbackend.security.code

enum class CodeIncludes(val source: Collection<Char>) {
    NUMBERS(('0'..'9').toSet()),
    LETTERS_LOWERCASE(('a'..'z').toSet()),
    LETTERS_UPPERCASE(('A'..'Z').toSet())
}
