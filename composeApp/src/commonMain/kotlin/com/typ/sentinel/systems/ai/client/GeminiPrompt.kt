package com.typ.sentinel.systems.ai.client

data class GeminiPrompt(
    val content: String,
    val role: String = "user"
)

fun botPrompt(content: String) = GeminiPrompt(content, role = "ai")
fun userPrompt(content: String) = GeminiPrompt(content, role = "user")