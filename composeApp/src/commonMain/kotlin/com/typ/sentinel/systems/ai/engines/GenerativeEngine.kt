package com.typ.sentinel.systems.ai.engines

import com.typ.sentinel.systems.ai.client.GeminiClient

class GenerativeEngine : AIEngine(
    client = GeminiClient()
)