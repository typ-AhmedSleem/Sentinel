package com.typ.sentinel.systems.rae

enum class RiskLevel {
    LOW,        // Minimal risk, likely safe
    MEDIUM,     // Somewhat suspicious, requires user caution
    HIGH,       // Clearly dangerous, strong spam/fraud indicators
    CRITICAL    // Severe risk, highly likely a scam/phishing attack
}