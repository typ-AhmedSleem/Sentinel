package com.typ.sentinel.systems.ai.client

import com.typ.sentinel.systems.nis.InterceptedNotification

data class GeminiPrompt(
    val content: String,
    val role: String = "user"
)

fun botPrompt(content: String) = GeminiPrompt(content, role = "ai")
fun userPrompt(content: String) = GeminiPrompt(content, role = "user")
fun createRiskAnalysisPrompt(
    language: String = "english",
    notification: InterceptedNotification,
    sender: String = notification.title,
    messageLength: Int = notification.content.length
) = userPrompt(
    """
        You are a highly advanced AI that specializes in analyzing messages for potential risks such as spam, scams, phishing, fraud and safe. 
        Your task is to analyze the following message and classify its risk level.

        📨 **Message Details:**
        - Title: "$sender"
        - Content: "${notification.content}"
        - MessageLength: $messageLength
        - Language: "$language"

        🎯 **Analysis Instructions:**
        - Identify if the message is **SAFE, SPAM_SCAM, FAKE_LOAN, PHISHING, or FRAUD**.
        - Assign a **Risk Level**: LOW, MEDIUM, HIGH, or CRITICAL.
        - Provide a **confidence score (0.0 - 1.0)** indicating how certain you are.
        - Explain why you classified the message that way.
        - Extract keywords or suspicious elements.
        - Detect any **patterns** like links, financial terms, or phishing attempts.
        - Suggest recommended actions and shouldn't be outside of ("mark_as_safe", "report", "ask_ai").
        - Provide all the output in this language: $language

        🔹 **Response Object Schema (JSON)**:
        {
            "category": "CATEGORY",
            "risk_level": "RISK_LEVEL",
            "confidence_score": CONFIDENCE_SCORE,
            "explanation": "EXPLANATION",
            "reasons": ["REASON1", "REASON2", ...],
            "keywords": ["KEYWORD1", "KEYWORD2", ...],
            "suspicious_elements": ["ELEMENT1", "ELEMENT2", ...],
            "sender": "SENDER_INFO",
            "language": "LANGUAGE",
            "detected_patterns": ["PATTERN1", "PATTERN2", ...],
            "recommended_actions": ["ACTION1", "ACTION2", ...],
            "detectedPatterns": ["PATTERN1", "PATTERN2", ...],
            "suspiciousElements": ["ELEMENT1", "ELEMENT2", ...],
        }

        Please return **only the JSON response** without any additional text.
    """.trimIndent()
)
