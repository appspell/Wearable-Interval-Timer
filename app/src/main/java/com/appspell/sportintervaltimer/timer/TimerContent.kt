package com.appspell.sportintervaltimer.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.appspell.sportintervaltimer.R
import com.appspell.sportintervaltimer.R.drawable
import com.appspell.sportintervaltimer.timer.TimerType.PREPARE
import com.appspell.sportintervaltimer.timer.TimerType.REST
import com.appspell.sportintervaltimer.timer.TimerType.UNDEFINED
import com.appspell.sportintervaltimer.timer.TimerType.WORK

@Composable
fun TimerContent(
    viewModel: TimerViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        timeText = {
            TimeText()
        },
    ) {
        TimerCountDown(
            setsText = state.sets,
            timerText = state.time,
            type = state.type,
            progress = state.progress
        )
    }
}

@Composable
private fun TimerCountDown(
    setsText: String,
    timerText: String,
    type: TimerType,
    progress: Float
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 4.dp,
        )

        Text(
            text = timerText,
            fontWeight = FontWeight.Light,
            style = MaterialTheme.typography.title1,
            textAlign = TextAlign.Center,
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.4f)
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = setsText,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.caption1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(4.dp)
                    )
                    Text(
                        text = when(type) {
                            PREPARE -> stringResource(id = R.string.type_prepare)
                            WORK ->  stringResource(id = R.string.type_work)
                            REST ->  stringResource(id = R.string.type_rest)
                            UNDEFINED -> ""
                        },
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            Box(modifier = Modifier.weight(0.2f)) {
                // Spacer
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(4.dp),
                    colors = ButtonDefaults.secondaryButtonColors(),
                ) {
                    Icon(
                        painter = painterResource(id = drawable.ic_pause),
                        contentDescription = stringResource(id = R.string.button_pause)
                    )
//                Icon(
//                    painter = painterResource(id = drawable.ic_plus),
//                    contentDescription = stringResource(id = R.string.button_continue)
//                )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(4.dp),
                    colors = ButtonDefaults.secondaryButtonColors(),
                ) {
                    Icon(
                        painter = painterResource(id = drawable.ic_skip),
                        contentDescription = stringResource(id = R.string.button_skip)
                    )
                }
            }
        }
    }
}