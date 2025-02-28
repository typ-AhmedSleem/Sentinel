package com.typ.sentinel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.typ.sentinel.ui.HomeScreen
import com.typ.sentinel.ui.helper.RootContainer
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@Composable
@OptIn(ExperimentalCupertinoApi::class)
fun AppContent() {
    var navigator: Navigator? by remember {
        mutableStateOf(null)
    }
    var currentScreen: Screen? by remember(navigator?.lastItemOrNull) {
        mutableStateOf(navigator?.lastItemOrNull)
    }

    RootContainer {
        CupertinoScaffold { rootPaddings ->
            Navigator(HomeScreen) { nav ->
                SlideTransition(
                    navigator = nav,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = rootPaddings.calculateTopPadding(),
                            bottom = rootPaddings.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                ) { screen ->
                    currentScreen = screen
                    screen.Content()
                }
                navigator = nav
            }
        }
    }
}