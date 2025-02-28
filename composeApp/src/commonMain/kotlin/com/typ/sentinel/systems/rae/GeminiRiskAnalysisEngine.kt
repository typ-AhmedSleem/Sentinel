package com.typ.sentinel.systems.rae

import com.typ.sentinel.systems.ai.client.GeminiPromptsFactory
import com.typ.sentinel.systems.ai.engines.AIEngine
import com.typ.sentinel.systems.nis.InterceptedNotification

class GeminiRiskAnalysisEngine(private val aiEngine: AIEngine) : RiskAnalysisEngine {

    override suspend fun analyze(
        language: String,
        notification: InterceptedNotification,
        sender: String,
        messageLength: Int
    ): RiskAnalysisEngineResult {
        val prompt = GeminiPromptsFactory.createRiskAnalysisPrompt(
            sender = sender,
            language = language,
            notification = notification,
            messageLength = messageLength
        )
        return aiEngine.generateFromPrompt(prompt).let { response ->
            RiskAnalysisResultResolver.resolveRawResponse(response)
        }
    }
}