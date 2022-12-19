package com.appspell.sportintervaltimer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.appspell.sportintervaltimer.R.string
import com.appspell.sportintervaltimer.theme.MainTheme
import com.appspell.sportintervaltimer.timersetup.TimerSetupViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private val BUTTON_SIZE = 38.dp

@Composable
fun TimerSetupContent(
    viewModel: TimerSetupViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val listState = rememberScalingLazyListState()
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        // TODO revisit navigation
        viewModel.navigation
            .onEach { newNavigationEvent ->
                navController.navigate(newNavigationEvent.route)
            }.launchIn(this)
    }

    MainTheme {
        Scaffold(
            timeText = {
                TimeText()
            },
            vignette = {
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            }
        ) {
            SetUpIntervalsContent(
                setsText = state.sets,
                workText = state.work,
                restText = state.rest,
                totalText = state.totalTimeSeconds.secondsToTimeDuration(),
                onStart = { viewModel.onSave() },
                onSetsAdd = { viewModel.onSetsAdd() },
                onSetsRemove = { viewModel.onSetsRemove() },
                onWorkAdd = { viewModel.onWorkAdd() },
                onWorkRemove = { viewModel.onWorkRemove() },
                onRestAdd = { viewModel.onRestAdd() },
                onRestRemove = { viewModel.onRestRemove() },
                listState = listState
            )
        }
    }
}

@Composable
private fun Int.secondsToTimeDuration(): String {
    val hours = this / 60 / 60
    val minutes = (this / 60) - hours

    val hoursText = if (hours > 1) {
        stringResource(id = string.hours, hours) + " "
    } else {
        ""
    }
    val minutesText = stringResource(id = string.minutes, minutes)
    val totalText = "$hoursText$minutesText"
    return totalText
}

@Composable
private fun SetUpIntervalsContent(
    setsText: String,
    workText: String,
    restText: String,
    totalText: String,
    onStart: () -> Unit,
    onSetsAdd: () -> Unit,
    onSetsRemove: () -> Unit,
    onWorkAdd: () -> Unit,
    onWorkRemove: () -> Unit,
    onRestAdd: () -> Unit,
    onRestRemove: () -> Unit,
    listState: ScalingLazyListState,
) {
    ScalingLazyColumn(
        state = listState,
        contentPadding = PaddingValues(top = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            TimerSetupStartButton(
                onClick = onStart,
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_set),
                defaultValue = setsText,
                onButtonAdd = onSetsAdd,
                onButtonRemove = onSetsRemove
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_work),
                defaultValue = workText,
                onButtonAdd = onWorkAdd,
                onButtonRemove = onWorkRemove
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_rest),
                defaultValue = restText,
                onButtonAdd = onRestAdd,
                onButtonRemove = onRestRemove
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.caption2,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.total_time, totalText),
            )
        }
        item {
            TimerSetupStartButton(
                onClick = onStart,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    // Scroll to the first item
    LaunchedEffect(Unit) {
        listState.scrollToItem(0)
    }
}

@Composable
private fun TimePickerRow(
    modifier: Modifier,
    rowName: String,
    defaultValue: String,
    onButtonAdd: () -> Unit,
    onButtonRemove: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 0.dp)
    ) {
        Button(
            onClick = { onButtonRemove() },
            colors = ButtonDefaults.secondaryButtonColors(),
            modifier = Modifier
                .align(CenterVertically)
                .size(BUTTON_SIZE)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = stringResource(id = R.string.button_minus)
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .weight(1.0f)
                .align(CenterVertically)
        ) {
            Text(
                text = rowName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption3,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = defaultValue,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Button(
            onClick = { onButtonAdd() },
            colors = ButtonDefaults.secondaryButtonColors(),
            modifier = Modifier
                .align(CenterVertically)
                .padding(top = 8.dp)
                .size(BUTTON_SIZE)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(id = R.string.button_plus)
            )
        }
    }
}

@Composable
private fun TimerSetupStartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Text(text = stringResource(R.string.timer_setup_start_button))
    }
}