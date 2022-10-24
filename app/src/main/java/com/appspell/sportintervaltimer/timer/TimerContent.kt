package com.appspell.sportintervaltimer.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
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
import com.appspell.sportintervaltimer.R.string
import com.appspell.sportintervaltimer.theme.PrepareTheme
import com.appspell.sportintervaltimer.theme.RestTheme
import com.appspell.sportintervaltimer.theme.WorkTheme
import com.appspell.sportintervaltimer.timer.TimerType.PREPARE
import com.appspell.sportintervaltimer.timer.TimerType.REST
import com.appspell.sportintervaltimer.timer.TimerType.UNDEFINED
import com.appspell.sportintervaltimer.timer.TimerType.WORK
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun TimerContent(
    viewModel: TimerViewModel = hiltViewModel(), navController: NavHostController
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect("navigation") {
        viewModel.navigation.onEach { newNavigationEvent ->
                navController.popBackStack()
                navController.navigate(
                    newNavigationEvent.route, NavOptions.Builder().setLaunchSingleTop(true).build()
                )
            }.launchIn(this)
    }

    when (state.type) {
        WORK -> WorkTheme {
            TimerScreenContent(state = state,
                onPause = { viewModel.onPause() },
                onResume = { viewModel.onResume() },
                onSkip = { viewModel.onSkip() })
        }
        REST -> RestTheme {
            TimerScreenContent(state = state,
                onPause = { viewModel.onPause() },
                onResume = { viewModel.onResume() },
                onSkip = { viewModel.onSkip() })
        }
        else -> PrepareTheme {
            TimerScreenContent(state = state,
                onPause = { viewModel.onPause() },
                onResume = { viewModel.onResume() },
                onSkip = { viewModel.onSkip() })
        }
    }
}

@Composable
private fun TimerScreenContent(
    state: TimerUiState, onPause: () -> Unit, onResume: () -> Unit, onSkip: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        timeText = {
            TimeText(
                timeTextStyle = MaterialTheme.typography.caption2, modifier = Modifier.padding(8.dp)
            )
        },
    ) {
        TimerCountDown(
            setsText = stringResource(
                string.number_of_number, state.currentSet, state.allSets
            ),
            timerText = state.time,
            type = state.type,
            progress = state.progress,
            isPaused = state.isPaused,
            onPause = onPause,
            onResume = onResume,
            onSkip = onSkip
        )
    }
}

@Composable
private fun TimerCountDown(
    setsText: String,
    timerText: String,
    type: TimerType,
    progress: Float,
    isPaused: Boolean,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onSkip: () -> Unit,
) {
    // TODO replace with Constraint Layout, current implementation might have issues with different watch sizes
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 8.dp,
            modifier = Modifier.fillMaxSize(),
        )

        // Time
        Text(
            text = timerText,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.display1,
            color = MaterialTheme.colors.primary,
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
                    // sets left
                    Text(
                        text = setsText,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.caption1,
                        color = MaterialTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    // type of activity
                    Text(
                        text = when (type) {
                            PREPARE -> stringResource(id = R.string.type_prepare)
                            WORK -> stringResource(id = R.string.type_work)
                            REST -> stringResource(id = R.string.type_rest)
                            UNDEFINED -> ""
                        },
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption2,
                        color = MaterialTheme.colors.secondaryVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Box(modifier = Modifier.weight(0.3f)) {
                // Spacer
            }
            // Action buttons (play & skip)
            TimerActionButtons(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                isPaused = isPaused,
                onPause = onPause,
                onResume = onResume,
                onSkip = onSkip
            )
        }
    }
}

@Composable
private fun TimerActionButtons(
    modifier: Modifier,
    isPaused: Boolean,
    onPause: () -> Unit,
    onResume: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = modifier
    ) {
        if (!isPaused) {
            Button(
                onClick = onPause,
                modifier = Modifier
                    .size(46.dp)
                    .padding(4.dp),
            ) {
                Icon(
                    painter = painterResource(id = drawable.ic_pause),
                    contentDescription = stringResource(id = string.button_pause)
                )
            }
        } else {
            Button(
                onClick = onResume,
                modifier = Modifier
                    .size(46.dp)
                    .padding(4.dp),
            ) {
                Icon(
                    painter = painterResource(id = drawable.ic_play),
                    contentDescription = stringResource(id = string.button_continue)
                )
            }
        }

        Button(
            onClick = onSkip,
            modifier = Modifier
                .size(46.dp)
                .padding(4.dp),
            colors = ButtonDefaults.iconButtonColors(),
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_skip),
                contentDescription = stringResource(id = string.button_skip)
            )
        }
    }
}