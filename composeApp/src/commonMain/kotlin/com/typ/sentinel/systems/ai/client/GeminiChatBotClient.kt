package com.typ.sentinel.systems.ai.client

expect class GeminiChatBotClient(history: List<GeminiPrompt> = emptyList()) : AIClient {

    override suspend fun ask(prompts: List<GeminiPrompt>): String

    suspend fun sendMessage(messageContent: String): String?

}