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
import com.typ.sentinel.R
import com.typ.sentinel.systems.rae.RiskLevel
import com.typ.sentinel.systems.rae.SpamCategory
import com.typ.sentinel.systems.shs.SecureHistoryEntry
import com.typ.sentinel.systems.shs.SecureHistoryManager
import io.github.alexzhirkevich.cupertino.CupertinoText

actual object HistoryScreen : Screen {

    private fun readResolve(): Any = HistoryScreen

    @Composable
    override fun Content() {
        val manager = remember { SecureHistoryManager() }
        var history by remember {
            mutableStateOf(emptyList<SecureHistoryEntry>())
        }
        LaunchedEffect(Unit) {
//            history = manager.getAllEntries()
            history = listOf(
                SecureHistoryEntry(
                    category = SpamCategory.SAFE,
                    confidenceLevel = 0.9f,
                    reasons = listOf("Reason 1", "Reason 2"),
                    riskLevel = RiskLevel.LOW,
                    id = 8172,
                    suggestedActions = listOf(),
                    sourceContent = null,
                    timestamp = 3722,
                    explanation = "vel",
                    keywords = listOf(),
                    suspiciousElements = listOf("vnjoiyq", "Sioasyf"),
                    sender = null,
                    language = "arabic",
                    detectedPatterns = listOf(),
                ),
                SecureHistoryEntry(
                    id = 2280,
                    category = SpamCategory.SPAM_SCAM,
                    confidenceLevel = 2.3f,
                    reasons = listOf(),
                    riskLevel = RiskLevel.MEDIUM,
                    suggestedActions = listOf(),
                    sourceContent = null,
                    timestamp = 5106,
                    explanation = "vocent",
                    keywords = listOf(),
                    suspiciousElements = listOf("vocent", "lolkasdg", "askdjhuiua"),
                    sender = null,
                    language = "english",
                    detectedPatterns = listOf()
                ),
                SecureHistoryEntry(
                    id = 2570,
                    category = SpamCategory.FRAUD,
                    confidenceLevel = 6.7f,
                    reasons = listOf(),
                    riskLevel = RiskLevel.MEDIUM,
                    suggestedActions = listOf(),
                    sourceContent = null,
                    timestamp = 8421,
                    explanation = "dictum",
                    keywords = listOf(),
                    suspiciousElements = listOf(),
                    sender = null,
                    language = "vix",
                    detectedPatterns = listOf()
                )
            )
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
                            // todo: Navigate to HistoryEntryViewerScreen
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
            CupertinoText(
                text = entry.suspiciousElements.joinToString(
                    prefix = stringResource(R.string.suspecious),
                    separator = ", "
                ),
                style = TextStyle(fontSize = 14.sp)
            )
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
                        .toString(),
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

    private fun getConfidenceColor(score: Float): Color {
        return when {
            score > 0.8 -> Color.Red
            score > 0.5 -> Color(255, 207, 80)
            else -> Color.Green
        }
    }

    private fun getRiskColor(riskLevel: RiskLevel): Color {
        return when (riskLevel) {
            RiskLevel.CRITICAL -> Color.Red
            RiskLevel.HIGH -> Color(255, 207, 80)
            RiskLevel.MEDIUM -> Color.Blue
            RiskLevel.LOW -> Color.Green
        }
    }

    private fun getCategoryColor(category: SpamCategory): Color {
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