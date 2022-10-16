package com.appspell.sportintervaltimer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState

private val BUTTON_SIZE = 38.dp

@Preview(
    device = Devices.WEAR_OS_LARGE_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TimerSetupContent() {
    val listState = rememberScalingLazyListState()
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
        SetUpIntervalsContent()
    }
}

@Composable
private fun SetUpIntervalsContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 24.dp)
    ) {
        TimePickerRow(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            rowName = stringResource(id = R.string.timer_setup_set),
            defaultValue = "00:00",
            onButtonPlus = {},
            onButtonMinus = {}
        )
        TimePickerRow(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            rowName = stringResource(id = R.string.timer_setup_work),
            defaultValue = "00:00",
            onButtonPlus = {},
            onButtonMinus = {}
        )
        TimePickerRow(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            rowName = stringResource(id = R.string.timer_setup_rest),
            defaultValue = "00:00",
            onButtonPlus = {},
            onButtonMinus = {}
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {
            Text(text = stringResource(R.string.timer_setup_start_button))
        }
    }
}

@Composable
private fun TimePickerRow(
    modifier: Modifier,
    rowName: String,
    defaultValue: String,
    onButtonPlus: () -> Unit,
    onButtonMinus: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 0.dp)
    ) {
        Button(
            onClick = { onButtonPlus() },
            modifier = Modifier
                .align(CenterVertically)
                .size(BUTTON_SIZE)
        ) {
            Text(
                text = stringResource(id = R.string.button_plus),
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
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = defaultValue,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Button(
            onClick = { onButtonMinus() },
            modifier = Modifier
                .align(CenterVertically)
                .padding(top = 8.dp)
                .size(BUTTON_SIZE)
        ) {
            Text(
                text = stringResource(id = R.string.button_minus),
            )
        }
    }
}