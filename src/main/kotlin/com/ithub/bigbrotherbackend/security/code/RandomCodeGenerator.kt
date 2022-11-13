package com.ithub.bigbrotherbackend.security.code

import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class RandomCodeGenerator(env: Environment) {

    private val codeLength: Int
    private val codeIncludes: Set<CodeIncludes>

    init {
        codeLength = env.getRequiredProperty(ENV_CODE_LENGTH).toInt()
        codeIncludes = env
            .getRequiredProperty(ENV_CODE_INCLUDES)
            .split(',')
            .map { CodeIncludes.valueOf(it) }
            .toSet()

        check(codeIncludes.isNotEmpty()) {
            "You should add security.code.includes in .properties; Example: " +
                    "\"security.code.includes=NUMBERS,LETTERS_LOWERCASE,LETTERS_UPPERCASE\""
        }
    }

    fun generateCode() = generateCode(
        codeIncludes.map { it.source }.reduce { acc, source -> acc + source },
        "",
        codeLength
    )


    fun generateCode(
        source: Collection<Char>,
        joint: String,
        length: Int
    ): String = List(length) { source.random() }.joinToString(joint)

    companion object {
        private const val ENV_CODE_LENGTH = "security.code.length"
        private const val ENV_CODE_INCLUDES = "security.code.includes"
    }

}
