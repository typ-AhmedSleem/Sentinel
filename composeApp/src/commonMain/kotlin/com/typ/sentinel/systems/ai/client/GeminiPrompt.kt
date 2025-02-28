package com.typ.sentinel.systems.ai.client

import com.typ.sentinel.systems.nis.InterceptedNotification

data class GeminiPrompt(
    val content: String,
    val role: String = "user"
)