package com.typ.sentinel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.typ.sentinel.systems.ai.client.createRiskAnalysisPrompt
import com.typ.sentinel.systems.ai.engines.GenerativeEngine
import com.typ.sentinel.systems.nis.InterceptedNotification
import com.typ.sentinel.systems.nis.NotificationsInterceptor
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoHorizontalDivider
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCupertinoApi::class)
@Composable
@Preview
fun App() {
    val logger = Logger("App")
    val genEngine = GenerativeEngine()
    val coroutineScope = rememberCoroutineScope()
    var latestNotification: InterceptedNotification? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        NotificationsInterceptor.notifications.collectLatest {
            latestNotification = it
        }
    }

    var geminiResponse: String? by remember { mutableStateOf(null) }
    CupertinoTheme {
        CupertinoScaffold { rootPaddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(rootPaddings),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                /* CupertinoText(
                     textAlign = TextAlign.Center,
                     modifier = Modifier.fillMaxWidth(),
                     text = latestNotification?.toString() ?: "No notifications yet!"
                 )*/

                CupertinoHorizontalDivider()

                CupertinoText(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = geminiResponse ?: "NO RESPONSE YET"
                )

                CupertinoButton(
                    onClick = {
                        coroutineScope.launch {
                            latestNotification?.let {
                                geminiResponse = "[THINKING....]"
                                geminiResponse = genEngine.generateFromPrompt(
                                    createRiskAnalysisPrompt(
                                        language = "arabic",
                                        notification = InterceptedNotification(
                                            title = "01029787124",
                                            content = " تهانينا! لقد تم اختيارك للحصول على قرض فوري بقيمة 100,000 جنيه بدون فوائد. سجل بياناتك الآن عبر: [loanservice.co](https://loanservice.co)",
                                        )
                                    )
                                )
                                    .also(logger::log)
                            }
                        }
                    },
                ) {
                    Text("Ask in Arabic")
                }

                CupertinoButton(
                    onClick = {
                        coroutineScope.launch {
                            latestNotification?.let { notification ->
                                geminiResponse = "[THINKING....]"
                                geminiResponse = genEngine.generateFromPrompt(
                                    createRiskAnalysisPrompt(
                                        language = "english",
                                        notification = InterceptedNotification(
                                            title = "BanK-AlAhly",
                                            content = "Your direct deposit of \$1,200 has been successfully processed into your account ending in 4567. Available balance: \$3,456.78.",
                                        )
                                    )
                                )
                                    .also(logger::log)
                            }
                        }
                    },
                ) {
                    Text("Ask in English")
                }

            }
        }
    }
}