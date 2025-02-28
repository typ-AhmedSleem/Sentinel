package com.typ.sentinel.systems.ucs

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

object UserControlSystem {
    private const val KEYSentinelShieldEnabled = "SentinelShieldEnabled"
    private const val KEYRiskSensitivityLevel = "RiskSensitivityLevel"
    private const val KEYAutoBlockHighRisk = "AutoBlockHighRisk"
    private const val KEYAiSuggestActionsEnabled = "AISuggestActionsEnabled"

    private val preferences = Settings()

    fun getSettings(): UserControlSettings {
        return UserControlSettings(
            isSentinelShieldEnabled = preferences[KEYSentinelShieldEnabled, true],
            riskSensitivityLevel = preferences[KEYRiskSensitivityLevel, 3],
            autoBlockHighRisk = preferences[KEYAutoBlockHighRisk, true],
            aiSuggestActionsEnabled = preferences[KEYAiSuggestActionsEnabled, true]
        )
    }
    fun updateSettings(newSettings: UserControlSettings) {
        preferences[KEYSentinelShieldEnabled] = newSettings.isSentinelShieldEnabled
        preferences[KEYRiskSensitivityLevel] = newSettings.riskSensitivityLevel
        preferences[KEYAutoBlockHighRisk] = newSettings.autoBlockHighRisk
        preferences[KEYAiSuggestActionsEnabled] = newSettings.aiSuggestActionsEnabled
    }

}