package com.typ.sentinel.systems.ai.engines

import com.typ.sentinel.systems.ai.client.GeminiChatBotClient

class ChatBasedEngine : AIEngine(client = GeminiChatBotClient()) {

    private val gemini = client as GeminiChatBotClient

    suspend fun askChat(question: String): ChatBotResponse {
        return ChatBotResponse(gemini.sendMessage(question) ?: "")
    }

}