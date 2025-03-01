package com.typ.sentinel.systems.ai.client

expect class GeminiChatBotClient(history: List<GeminiPrompt> = listOf(
    GeminiPrompt("You are a financial assistant that can detect spam, scam, fraud, phishing from content of given messages")
)) : AIClient {

    override suspend fun ask(prompts: List<GeminiPrompt>): String

    suspend fun sendMessage(messageContent: String): String?

}