package com.typ.sentinel.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

@Composable
fun RootContainer(content: @Composable () -> Unit) {
    CupertinoTheme(
        colorScheme = lightColorScheme(
            accent = Color.Black,
            label = Color.Black,
        )
    ) {
        content()
    }
}