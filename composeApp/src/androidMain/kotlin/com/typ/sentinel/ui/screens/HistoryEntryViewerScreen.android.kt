package com.typ.sentinel.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.typ.sentinel.R
import com.typ.sentinel.systems.shs.SecureHistoryEntry
import com.typ.sentinel.systems.shs.SecureHistoryManager
import com.typ.sentinel.ui.screens.HistoryScreen.Badge
import com.typ.sentinel.ui.screens.HistoryScreen.getCategoryColor
import com.typ.sentinel.ui.screens.HistoryScreen.getConfidenceColor
import com.typ.sentinel.ui.screens.HistoryScreen.getRiskColor
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

actual class HistoryEntryViewerScreen actual constructor(private val entryId: Long) : Screen {

    @Composable
    override fun Content() {
        var ent: SecureHistoryEntry? by remember {
            mutableStateOf(null)
        }
        LaunchedEffect(Unit) {
            ent = SecureHistoryManager().getEntryById(entryId)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ent?.let { entry ->
                item {
                    CupertinoText(
                        text = stringResource(R.string.history_details),
                        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, CupertinoTheme.colorScheme.accent, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        
                        CupertinoText(text = entry.sender ?: "Unknown Sender", style = CupertinoTheme.typography.title2)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Badge(text = entry.category.name, color = getCategoryColor(entry.category))
                            Badge(text = entry.riskLevel.name, color = getRiskColor(entry.riskLevel))
                            Badge(
                                text = "Confidence: ${(entry.confidenceLevel * 100).toInt()}%",
                                color = getConfidenceColor(entry.confidenceLevel)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        CupertinoText(
                            text = "Sender: ${entry?.sender ?: "Unknown Sender"}",
                            style = CupertinoTheme.typography.body
                        )
                    }
                }

                item {
                    SelectionContainer {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    CupertinoTheme.colorScheme.secondarySystemBackground,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        ) {
                            CupertinoText(text = "Explanation:", style = CupertinoTheme.typography.title3)
                            Spacer(modifier = Modifier.height(4.dp))
                            CupertinoText(text = entry.explanation, style = CupertinoTheme.typography.body)
                        }
                    }
                }

                if (entry.keywords.isNotEmpty()) {
                    item {
                        TagSection(title = "Keywords", tags = entry.keywords)
                    }
                }

                if (entry.suspiciousElements.isNotEmpty()) {
                    item {
                        TagSection(title = "Suspicious Elements", tags = entry.suspiciousElements)
                    }
                }

                if (entry.detectedPatterns.isNotEmpty()) {
                    item {
                        TagSection(title = "Detected Patterns", tags = entry.detectedPatterns)
                    }
                }

                if (entry.suggestedActions.isNotEmpty()) {
                    item {
                        TagSection(title = "Suggested Actions", tags = entry.suggestedActions)
                    }
                }
            }
        }
    }

    @Composable
    fun TagSection(title: String, tags: List<String>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CupertinoTheme.colorScheme.tertiarySystemBackground, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            CupertinoText(text = "$title:", style = CupertinoTheme.typography.title3)
            Spacer(modifier = Modifier.height(4.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                tags.forEach { tag ->
                    Badge(text = tag, color = CupertinoTheme.colorScheme.secondaryLabel)
                }
            }
        }
    }

}