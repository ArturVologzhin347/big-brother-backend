package com.ithub.bigbrotherbackend.security.code

import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.stereotype.Component

@Component
class CodeGenerator(env: Environment) {

    private val CODE_LENGTH: Int
    private val CODE_INCLUDES: Set<CodeIncludes>

    init {
        CODE_LENGTH = env.getRequiredProperty(ENV_CODE_LENGTH).toInt()
        CODE_INCLUDES = env
            .getRequiredProperty(ENV_CODE_INCLUDES)
            .split(',')
            .map { CodeIncludes.valueOf(it) }
            .toSet()
        
    }












    companion object {
        private const val ENV_CODE_LENGTH = "security.code.length"
        private const val ENV_CODE_INCLUDES = "security.code.includes"
    }

}
