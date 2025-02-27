package com.typ.sentinel.systems.ai.client

data class GeminiPrompt(
    val content: String,
    val role: String = "user"
)