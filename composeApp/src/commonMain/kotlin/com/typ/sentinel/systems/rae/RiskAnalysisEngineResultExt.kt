package com.typ.sentinel.systems.rae

import com.typ.sentinel.systems.ucs.UserControlSettings

fun RiskAnalysisEngineResult.isHighRisk(sensitivity: Int): Boolean {
    val threshold = when (sensitivity) {
        1 -> 0.9f  // Only flag messages with 90%+ confidence.
        2 -> 0.75f // Flag messages with 75%+ confidence.
        3 -> 0.6f  // Default sensitivity.
        4 -> 0.4f  // Be more cautious.
        5 -> 0.2f  // Flag almost anything suspicious.
        else -> 0.6f // Default.
    }
    return confidenceLevel >= threshold
}

fun RiskAnalysisEngineResult.shouldAutoBlock(settings: UserControlSettings): Boolean {
    return settings.autoBlockHighRisk && this.isHighRisk(settings.riskSensitivityLevel)
}