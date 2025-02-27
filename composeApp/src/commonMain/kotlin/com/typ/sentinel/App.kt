package com.typ.sentinel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
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
import com.typ.sentinel.systems.ai.client.userPrompt
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
                    .padding(rootPaddings),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                CupertinoText(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = latestNotification?.toString() ?: "No notifications yet!"
                )

                CupertinoHorizontalDivider()

                CupertinoText(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = geminiResponse ?: "NO RESPONSE YET"
                )
                CupertinoButton(
                    onClick = {
                        coroutineScope.launch {
                            geminiResponse = "[THINKING....]"
                            val promptContent = buildString {
                                append(
                                    "You are an AI trained to classify financial messages." +
                                            "You need to classify the message to detect possible spam, scam, fraud" +
                                            "or mark it as safe if it isn't that.\n"
                                )
                                append("Analyze the following message:\n")
                                append("title: ${latestNotification?.title ?: "No Title"}\n")
                                append("content: ${latestNotification?.content ?: "No Content"}")
                            }
                            geminiResponse = genEngine.generateFromPrompt(
//                                userPrompt(content = promptContent)
                                userPrompt(
                                    "أنت مساعد ذكي متخصص في تصنيف الرسائل المالية.  \n" +
                                            "قم بتحليل الرسالة التالية، ثم صنّفها في إحدى الفئات التالية:  \n" +
                                            "- \"spam\" (رسالة مزعجة أو إعلانية غير مرغوب فيها)  \n" +
                                            "- \"scam\" (احتيال مالي أو نصب)  \n" +
                                            "- \"phishing\" (محاولة تصيد وسرقة بيانات)  \n" +
                                            "- \"safe\" (رسالة آمنة شرعية)  \n" +
                                            "\n" +
                                            "بعد التصنيف، أعد الإجابة بتنسيق JSON كما يلي:  \n" +
                                            "{\n" +
                                            "  \"category\": \"CATEGORY\",\n" +
                                            "  \"risk_level\": \"RISK_LEVEL\",\n" +
                                            "  \"confidence_score\": CONFIDENCE_SCORE,\n" +
                                            "  \"explanation\": \"EXPLANATION\",\n" +
                                            "  \"keywords\": [\"KEYWORD1\", \"KEYWORD2\", ...],\n" +
                                            "  \"suspicious_elements\": [\"ELEMENT1\", \"ELEMENT2\", ...],\n" +
                                            "  \"sender\": \"SENDER_INFO\",\n" +
                                            "  \"message_length\": MESSAGE_LENGTH,\n" +
                                            "  \"language\": \"LANGUAGE\",\n" +
                                            "  \"detected_patterns\": [\"PATTERN1\", \"PATTERN2\", ...],\n" +
                                            "  \"timestamp\": \"YYYY-MM-DDTHH:MM:SSZ\",\n" +
                                            "  \"recommended_actions\": [\"ACTION1\", \"ACTION2\", ...]\n" +
                                            "}\n" +
                                            "\n" +
                                            "\uD83D\uDD39 **الرسائل لتحليلها:** 1: \"\uD83C\uDF89 عرض خاص لفترة محدودة! احجز تذكرتك الآن واستمتع بتخفيض 50% على الرحلات الجوية. قم بالحجز عبر: [travel-offer.me](https://travel-offer.me)\"\n" +
                                            "\n" +
                                            "2: \"\uD83D\uDCCC تهانينا! لقد تم اختيارك للحصول على قرض فوري بقيمة 100,000 جنيه بدون فوائد. سجل بياناتك الآن عبر: [loanservice.co](https://loanservice.co)\"\n" +
                                            "\n" +
                                            "3:\"\uD83D\uDEA8 تم تسجيل محاولة غير مصرح بها على حسابك المصرفي. لمنع الإغلاق، قم بتحديث بياناتك الآن عبر: [banking-auth.net](https://banking-auth.net)\"\n" +
                                            "\n" +
                                            "4:\"تم إضافة تحويل لحظي لبطاقتكم مسبقة الدفع بمبلغ 30.00 جم من احمد حاتم السيد عبدالعزيز عسكوره رقم مرجعي 505720847094 يوم 02-26 الساعة 17:09 للمزيد اتصل بـ 19623\""
                                )
                            ).also {
                                logger.log(it)
                            }
                        }
                    },
                ) {
                    Text("Analyze the notification")
                }

            }
        }
    }
}