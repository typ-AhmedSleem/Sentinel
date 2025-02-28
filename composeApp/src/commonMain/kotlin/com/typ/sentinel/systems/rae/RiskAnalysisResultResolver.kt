package com.typ.sentinel.systems.rae

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object RiskAnalysisResultResolver {
    fun resolveRawResponse(rawResponse: String): RiskAnalysisEngineResult {
        // * Remove unwanted prefix and suffix (if found)
        return rawResponse
            .removePrefix("```json")
            .removeSuffix("```")
            .let { readyJsonString -> Json.parseToJsonElement(readyJsonString).jsonObject }
            .let { json ->
                // * Resolve the ready response to 'RiskAnalysisEngineResult'
                RiskAnalysisEngineResult(
                    category = SpamCategory.valueOf(json["category"]!!.jsonPrimitive.content),
                    riskLevel = RiskLevel.valueOf(json["risk_level"]!!.jsonPrimitive.content),
                    confidenceLevel = json["confidence_score"]!!.jsonPrimitive.float,
                    reasons = json["reasons"]!!.jsonArray.map { it.jsonPrimitive.content },
                    suggestedActions = json["suggested_actions"]!!.jsonArray.map { it.jsonPrimitive.content },
                    timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    explanation = json["explanation"]!!.jsonPrimitive.content,
                    keywords = json["keywords"]!!.jsonArray.map { it.jsonPrimitive.content },
                    suspiciousElements = json["suspicious_elements"]!!.jsonArray.map { it.jsonPrimitive.content },
                    sender = json["sender"]!!.jsonPrimitive.content,
                    language = json["language"]!!.jsonPrimitive.content,
                    detectedPatterns = json["detected_patterns"]!!.jsonArray.map { it.jsonPrimitive.content },
                    sourceContent = json["sourceContent"]?.jsonPrimitive?.contentOrNull
                )
            }
    }
}
