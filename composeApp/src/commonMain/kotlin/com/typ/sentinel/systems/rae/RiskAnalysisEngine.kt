package com.typ.sentinel.systems.rae

import com.typ.sentinel.systems.nis.InterceptedNotification

interface RiskAnalysisEngine {
    suspend fun analyze(
        language: String = "english",
        notification: InterceptedNotification,
        sender: String = notification.title,
        messageLength: Int = notification.content.length
    ): RiskAnalysisEngineResult
}