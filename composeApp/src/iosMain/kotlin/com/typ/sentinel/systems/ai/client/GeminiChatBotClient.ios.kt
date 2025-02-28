package com.typ.sentinel.systems.ai.client

actual class GeminiChatBotClient actual constructor(history: List<GeminiPrompt>) : AIClient {
    actual override suspend fun ask(prompts: List<GeminiPrompt>): String {
        TODO("Not yet implemented")
    }

    actual suspend fun sendMessage(messageContent: String): String? {
        TODO("Not yet implemented")
    }
}