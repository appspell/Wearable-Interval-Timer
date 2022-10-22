package com.appspell.sportintervaltimer.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MainThemeColors,
        content = content
    )
}

@Composable
fun PrepareTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = PrepareThemeColors,
        content = content
    )
}

@Composable
fun WorkTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = WorkThemeColors,
        content = content
    )
}

@Composable
fun RestTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = RestThemeColors,
        content = content
    )
}