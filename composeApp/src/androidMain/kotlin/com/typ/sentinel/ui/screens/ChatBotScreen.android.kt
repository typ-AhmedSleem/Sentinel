package com.typ.sentinel.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.typ.sentinel.R
import com.typ.sentinel.systems.ai.engines.ChatBasedEngine
import com.typ.sentinel.systems.ai.engines.ChatBotResponse
import com.typ.sentinel.ui.helper.ChatBotUiState
import io.github.alexzhirkevich.cupertino.CupertinoHorizontalDivider
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTextField
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.launch

actual object ChatBotScreen : Screen {

    private fun readResolve(): Any = ChatBotScreen

    @Composable
    override fun Content() {
        val ctx = LocalContext.current
        val chatBot = remember {
            ChatBasedEngine()
        }
        var uiState by remember {
            mutableStateOf(ChatBotUiState.READY)
        }
        var messages by remember {
            mutableStateOf(
                listOf(false to "أنا مساعد مالي متخصص في اكتشاف الرسائل الاحتيالية والبريد العشوائي والتصيد الاحتيالي بناءً على محتوى الرسائل. يمكنني مساعدتك في تحديد ما إذا كانت الرسالة مشبوهة أو قد تكون محاولة لخداعك")
            )
        }
        var inputText by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 16.dp,
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 16.dp,
                )
        ) {
            // Chat Messages
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CupertinoText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.chat_bot),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(4.dp))
                CupertinoHorizontalDivider()
                Spacer(Modifier.height(4.dp))
                messages.forEach { (isUser, message) ->
                    ChatBubble(isUser, message)
                }
            }

            // Input Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        CupertinoTheme.colorScheme.accent.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 2.dp,
                        start = 2.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CupertinoTextField(
                    enabled = uiState == ChatBotUiState.READY,
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { CupertinoText(stringResource(R.string.type_a_message)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = oc@{
                            if (uiState != ChatBotUiState.READY) return@oc
                            if (inputText.isEmpty()) {
                                Toast.makeText(ctx, R.string.cant_send_empty_msg, Toast.LENGTH_SHORT).show()
                                return@oc
                            }
                            uiState = ChatBotUiState.THINKING
                            coroutineScope.launch {
                                messages = messages + (true to inputText)
                                val response = try {
                                    chatBot.askChat(inputText)
                                } catch (e: Throwable) {
                                    e.printStackTrace()
                                    ChatBotResponse("")
                                }
                                if (response.isEmpty) {
                                    uiState = ChatBotUiState.READY
                                    Toast.makeText(ctx, R.string.failed_to_send_message, Toast.LENGTH_SHORT).show()
                                    return@launch
                                }
                                messages = messages + (false to response.message.trim())
                                uiState = ChatBotUiState.READY
                                inputText = ""
                            }
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            vertical = 8.dp,
                            horizontal = 8.dp
                        )
                )
            }
        }
    }

    @Composable
    fun ChatBubble(isUser: Boolean, message: String) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CupertinoText(
                text = message,
                color = if (isUser) Color.White else Color.Black,
                textAlign = if (isUser) TextAlign.End else TextAlign.Start,
                modifier = Modifier
                    .background(
                        color = if (isUser) Color(45, 51, 107) else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
                    .align(if (isUser) Alignment.CenterEnd else Alignment.CenterStart)

            )
        }
    }
}