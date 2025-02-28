package com.typ.sentinel.systems.ai.client

import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.typ.sentinel.BuildConfig

actual class GeminiChatBotClient actual constructor(history: List<GeminiPrompt>) : AIClient {

    private val gemini = GenerativeModel(
        modelName = GeminiModels.GEMINI_FLASH_2_0,
        apiKey = BuildConfig.GEMINI_API_KEY
    )
    private val chat = Chat(
        model = gemini,
        history = history
            .map {
                content(role = it.role) {
                    text(it.content)
                }
            }.toMutableList()
    )


    actual override suspend fun ask(prompts: List<GeminiPrompt>): String {
        return gemini.generateContent(
            *prompts.map { prompt ->
                content(role = prompt.role) {
                    text(prompt.content)
                }
            }.toTypedArray()
        ).text ?: ""
    }

    actual suspend fun sendMessage(messageContent: String): String? {
        return chat.sendMessage(content { text(messageContent) }).text
    }

}