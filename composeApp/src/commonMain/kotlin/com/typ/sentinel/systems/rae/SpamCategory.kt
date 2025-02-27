package com.typ.sentinel.systems.rae

enum class SpamCategory {
    SAFE,               // ✅ Not harmful, no action needed.
    SPAM_SCAM,          // 🚨 Generic scam/spam messages (e.g., lottery, fake giveaways).
    FAKE_LOAN,          // 💰 Fraudulent loan offers with high-interest scams.
    PHISHING,           // 🎣 Fake login attempts to steal credentials.
    FRAUD               // 🔐 Suspicious account access or transaction alerts.
}