package com.typ.sentinel.systems.ai.client

expect open class GeminiClient() : AIClient {

    override suspend fun ask(prompts: List<GeminiPrompt>) : String

}