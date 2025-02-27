package com.typ.sentinel.systems.ai.client

actual open class GeminiClient actual constructor() : AIClient {

    actual override suspend fun ask(prompts: List<GeminiPrompt>): String {
        TODO("Not yet implemented")
    }

}