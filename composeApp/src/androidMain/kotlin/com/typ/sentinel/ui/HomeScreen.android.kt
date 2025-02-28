package com.typ.sentinel.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.typ.sentinel.AppContent
import com.typ.sentinel.R
import com.typ.sentinel.systems.nis.NotificationInterceptorService
import com.typ.sentinel.systems.ucs.UserControlSettings
import com.typ.sentinel.systems.ucs.UserControlSystem
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Bookmark
import io.github.alexzhirkevich.cupertino.icons.outlined.ListBullet
import io.github.alexzhirkevich.cupertino.icons.outlined.Message
import io.github.alexzhirkevich.cupertino.icons.outlined.WrenchAndScrewdriver
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import pro.respawn.kmmutils.compose.ObserveLifecycle
import pro.respawn.kmmutils.compose.modifier.thenIf

actual object HomeScreen : Screen {

    private fun readResolve(): Any = HomeScreen

    @Composable
    override fun Content() {
        //  * Runtime * //
        val navigator = LocalNavigator.current
        var userControlSettings by remember {
            mutableStateOf(UserControlSystem.getSettings())
        }

        ObserveLifecycle {
            if (it == Lifecycle.Event.ON_RESUME) {
                userControlSettings = UserControlSystem.getSettings()
            }
        }
        // * UI * //
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(25)),
                painter = painterResource(id = R.drawable.ic_launcher_background),
            )
            Spacer(Modifier.height(32.dp))
            CupertinoText(
                text = stringResource(R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Spacer(Modifier.height(16.dp))
            CupertinoText(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.8f),
                text = stringResource(R.string.app_desc),
                fontWeight = FontWeight.Medium,
                fontSize = 26.sp
            )
            Spacer(Modifier.height(128.dp))

            ShieldCard(settings = userControlSettings)

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                Action(
                    icon = CupertinoIcons.Outlined.Bookmark,
                    title = R.string.history
                ) {

                }
                Action(
                    icon = CupertinoIcons.Outlined.Message,
                    title = R.string.chat_bot
                ) {

                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                Action(
                    icon = CupertinoIcons.Outlined.ListBullet,
                    title = R.string.lists
                ) {

                }
                Action(
                    icon = CupertinoIcons.Outlined.WrenchAndScrewdriver,
                    title = R.string.settings
                ) {

                }
            }
        }
    }
}

@Composable
private fun ShieldCard(settings: UserControlSettings) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 96.dp)
            .thenIf(
                condition = settings.isSentinelShieldEnabled,
                ifTrue = {
                    border(
                        width = 3.dp,
                        color = Color(91, 145, 59),
                        shape = RoundedCornerShape(15)
                    )

                },
                ifFalse = {
                    border(
                        width = 2.dp,
                        color = Color(216, 64, 64),
                        shape = RoundedCornerShape(15)
                    )
                    clickable {
                        NotificationInterceptorService.startService()
                    }
                }
            )
            .clip(RoundedCornerShape(15))
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.Center
    ) {
        CupertinoText(
            text = stringResource(if (settings.isSentinelShieldEnabled) R.string.shield_enabled else R.string.shield_disabled),
            fontWeight = FontWeight.Bold,
            color = if (settings.isSentinelShieldEnabled) Color(91, 145, 59) else Color(216, 64, 64),
            fontSize = 22.sp,
        )
    }
}

@Composable
fun RowScope.Action(
    icon: ImageVector,
    title: Int, onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(0.5f)
            .heightIn(min = 96.dp)
            .border(
                width = 1.dp,
                color = CupertinoTheme.colorScheme.label,
                shape = RoundedCornerShape(15)
            )
            .clip(RoundedCornerShape(15))
            .clickable {
                NotificationInterceptorService.startService()
            }
            .clickable(onClick = onClick)
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = null,
            imageVector = icon,
            modifier = Modifier
                .size(48.dp)
        )
        CupertinoText(
            fontWeight = FontWeight.SemiBold,
            text = stringResource(title),
            lineHeight = 20.sp,
            fontSize = 18.sp,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppContent()
}