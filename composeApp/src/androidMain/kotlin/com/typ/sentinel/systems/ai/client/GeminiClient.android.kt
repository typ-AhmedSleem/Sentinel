package com.typ.sentinel.systems.ai.client

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.typ.sentinel.BuildConfig

actual open class GeminiClient actual constructor() : AIClient {

    private val gemini = GenerativeModel(
        modelName = GeminiModels.GEMINI_FLASH_2_0,
        apiKey = BuildConfig.GEMINI_API_KEY
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

}