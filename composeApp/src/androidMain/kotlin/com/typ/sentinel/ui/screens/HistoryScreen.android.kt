package com.typ.sentinel.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.typ.sentinel.R
import com.typ.sentinel.systems.rae.RiskLevel
import com.typ.sentinel.systems.rae.SpamCategory
import com.typ.sentinel.systems.shs.SecureHistoryEntry
import com.typ.sentinel.systems.shs.SecureHistoryManager
import io.github.alexzhirkevich.cupertino.CupertinoText
import kotlinx.datetime.Clock

actual object HistoryScreen : Screen {

    private fun readResolve(): Any = HistoryScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val manager = remember { SecureHistoryManager() }
        var history by remember {
            mutableStateOf(emptyList<SecureHistoryEntry>())
        }
        LaunchedEffect(Unit) {
            listOf(
                SecureHistoryEntry(
                    id = 1,
                    category = SpamCategory.PHISHING,
                    confidenceLevel = 0.92f,
                    reasons = listOf("Suspicious link detected", "Urgent action required"),
                    riskLevel = RiskLevel.HIGH,
                    suggestedActions = listOf("report", "analyze"),
                    sourceContent = "Your account is at risk! Click here to verify immediately: http://el_bank.xy",
                    timestamp = Clock.System.now().toEpochMilliseconds(),
                    explanation = "Detected a phishing attempt using a fake bank website.",
                    keywords = listOf("account", "verify", "click"),
                    suspiciousElements = listOf("http://el_bank.xy"),
                    sender = "Bank Support",
                    language = "English",
                    detectedPatterns = listOf("Fake URL pattern", "Urgency trigger")
                ),
                SecureHistoryEntry(
                    id = 2,
                    category = SpamCategory.FAKE_LOAN,
                    confidenceLevel = 0.85f,
                    reasons = listOf("High-interest scam detected", "Unverified lender"),
                    riskLevel = RiskLevel.MEDIUM,
                    suggestedActions = listOf("mark_as_safe", "ask_ai"),
                    sourceContent = "Get a loan up to \$50,000 with NO credit check! Apply now.",
                    timestamp = Clock.System.now().toEpochMilliseconds(),
                    explanation = "Detected a high-risk loan offer with unrealistic terms.",
                    keywords = listOf("loan", "credit check", "apply now"),
                    suspiciousElements = listOf("Unverified lender name"),
                    sender = "QuickLoanNow",
                    language = "English",
                    detectedPatterns = listOf("Loan scam pattern")
                ),
                SecureHistoryEntry(
                    id = 3,
                    category = SpamCategory.SAFE,
                    confidenceLevel = 0.99f,
                    reasons = listOf("No suspicious content detected"),
                    riskLevel = RiskLevel.LOW,
                    suggestedActions = emptyList(),
                    sourceContent = "Hey! Let’s catch up tomorrow for lunch.",
                    timestamp = Clock.System.now().toEpochMilliseconds(),
                    explanation = "No signs of spam or fraud detected.",
                    keywords = listOf("lunch", "catch up"),
                    suspiciousElements = emptyList(),
                    sender = "John Doe",
                    language = "English",
                    detectedPatterns = emptyList()
                )
            )
            history = manager.getAllEntries()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            CupertinoText(
                text = stringResource(R.string.history),
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(history) { entry ->
                    HistoryEntryItem(
                        entry = entry,
                        onClick = {
                            // Navigate to HistoryEntryViewerScreen
                            navigator?.push(HistoryEntryViewerScreen(entry.id))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    @Composable
    fun HistoryEntryItem(entry: SecureHistoryEntry, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(12.dp)
        ) {
            CupertinoText(
                text = entry.sender ?: "Unknown Sender",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (entry.suspiciousElements.isNotEmpty()) {
                CupertinoText(
                    style = TextStyle(fontSize = 14.sp),
                    text = entry.suspiciousElements.joinToString(
                        prefix = stringResource(R.string.suspecious),
                        separator = ", "
                    ),
                )
            }
            entry.sourceContent?.let {
                Spacer(modifier = Modifier.height(4.dp))
                CupertinoText(
                    text = it,
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Badge(
                    entry.confidenceLevel
                        .times(100)
                        .toInt()
                        .coerceAtMost(100)
                        .toString()
                        .plus(" %"),
                    getConfidenceColor(entry.confidenceLevel)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Badge(entry.riskLevel.name, getRiskColor(entry.riskLevel))
                Spacer(modifier = Modifier.width(8.dp))
                Badge(entry.category.name, getCategoryColor(entry.category))
            }
        }
    }

    @Composable
    fun Badge(text: String, color: Color) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            CupertinoText(text = text, style = TextStyle(fontSize = 12.sp, color = Color.White))
        }
    }

    fun getConfidenceColor(score: Float): Color {
        return when {
            score > 0.8 -> Color.Green
            score > 0.5 -> Color(255, 207, 80)
            else -> Color.Red
        }
    }

    fun getRiskColor(riskLevel: RiskLevel): Color {
        return when (riskLevel) {
            RiskLevel.CRITICAL -> Color.Red
            RiskLevel.HIGH -> Color(255, 207, 80)
            RiskLevel.MEDIUM -> Color.Blue
            RiskLevel.LOW -> Color.Green
        }
    }

    fun getCategoryColor(category: SpamCategory): Color {
        return when (category) {
            SpamCategory.SAFE -> Color(98, 111, 71)
            SpamCategory.SPAM_SCAM -> Color(255, 207, 80)
            SpamCategory.FAKE_LOAN -> Color.Magenta
            SpamCategory.PHISHING -> Color(45, 51, 107)
            SpamCategory.FRAUD -> Color.Red
            SpamCategory.BLOCKLIST -> Color.Red
        }
    }

}