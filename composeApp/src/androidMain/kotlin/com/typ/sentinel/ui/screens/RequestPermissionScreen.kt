package com.typ.sentinel.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.typ.sentinel.R
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

class RequestPermissionScreen : Screen {

    @OptIn(ExperimentalCupertinoApi::class)
    @Composable
    @Preview
    override fun Content() {
        CupertinoTheme {
            CupertinoScaffold {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        contentDescription = null,
                        modifier = Modifier
                            .size(192.dp)
                            .clip(RoundedCornerShape(25)),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                    )
                    Spacer(Modifier.height(64.dp))
                    CupertinoText(stringResource(R.string.app_name))
                    Spacer(Modifier.height(32.dp))
                    CupertinoText(stringResource(R.string.app_name))
                    Spacer(Modifier.height(128.dp))
                    CupertinoButton(
                        onClick = {

                        }
                    ) {
                        CupertinoText(stringResource(R.string.app_name))
                    }
                }
            }
        }
    }
}