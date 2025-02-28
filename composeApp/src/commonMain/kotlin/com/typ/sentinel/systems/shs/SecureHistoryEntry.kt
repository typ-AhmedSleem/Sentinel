package com.typ.sentinel.systems.shs

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.typ.sentinel.systems.rae.RiskLevel
import com.typ.sentinel.systems.rae.SpamCategory
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "secure_history")
data class SecureHistoryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val category: SpamCategory, // The classification category
    val confidenceLevel: Float, // Confidence score (0.0 - 1.0)
    val reasons: List<String>, // Explanations for the decision
    val riskLevel: RiskLevel, // Overall risk assessment
    val suggestedActions: List<String>, // Recommendations for user actions
    val sourceContent: String? = null, // The original intercepted notification content
    val timestamp: Long, // Timestamp of analysis
    val explanation: String, // AI-generated human-friendly explanation
    val keywords: List<String>, // Key spam indicators
    val suspiciousElements: List<String>, // Extracted suspicious words/phrases
    val sender: String?, // The sender's name/number
    val language: String, // Detected language (e.g., "ar", "en")
    val detectedPatterns: List<String>, // Regex or rule-based detections
)
