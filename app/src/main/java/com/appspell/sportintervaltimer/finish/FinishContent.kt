package com.appspell.sportintervaltimer.finish

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.appspell.sportintervaltimer.Navigation
import com.appspell.sportintervaltimer.R
import com.appspell.sportintervaltimer.theme.RestTheme

@Composable
fun FinishContent(
    navController: NavHostController
) {
    RestTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            timeText = {
                TimeText()
            },
            vignette = {
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        navController.popBackStack()
                        navController.navigate(
                            Navigation.TimerSetup.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .build()
                        )
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.finish_text),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.display1,
                )
                Text(
                    text = stringResource(id = R.string.tap_to_finish),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption1,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}