package com.typ.sentinel.systems.ai.engines

data class ChatBotResponse(
    val message: String,
) {
    val isEmpty = message.isEmpty()
}
