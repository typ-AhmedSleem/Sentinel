package com.typ.sentinel.systems.ucs

import kotlinx.serialization.Serializable

@Serializable
data class UserControlSettings(
    val isSentinelShieldEnabled: Boolean = true,
    val riskSensitivityLevel: Int = 3,
    val autoBlockHighRisk: Boolean = false,
    val aiSuggestActionsEnabled: Boolean = true
)