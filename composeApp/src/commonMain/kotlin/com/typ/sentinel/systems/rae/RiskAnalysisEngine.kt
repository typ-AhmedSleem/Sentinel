package com.typ.sentinel.systems.rae

import com.typ.sentinel.systems.nis.InterceptedNotification

interface RiskAnalysisEngine {
    suspend fun analyze(notification: InterceptedNotification): RiskAnalysisEngineResult
}