package com.typ.sentinel.systems.ai.client

interface AIClient {
    suspend fun ask(prompts: List<GeminiPrompt>): String
}