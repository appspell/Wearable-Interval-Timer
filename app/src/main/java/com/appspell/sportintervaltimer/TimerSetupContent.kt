package com.appspell.sportintervaltimer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        SetUpIntervalsContent(
            onStart = {},
            onSetsAdd = {},
            onSetsRemove = {},
            onWorkAdd = {},
            onWorkRemove = {},
            onRestAdd = {},
            onRestRemove = {},
            listState = listState
        )
    }
}

@Composable
private fun SetUpIntervalsContent(
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
                onClick = onStart
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_set),
                defaultValue = "00:00",
                onButtonAdd = onSetsAdd,
                onButtonRemove = onSetsRemove
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_work),
                defaultValue = "00:00",
                onButtonAdd = onWorkAdd,
                onButtonRemove = onWorkRemove
            )
        }
        item {
            TimePickerRow(
                modifier = Modifier
                    .fillMaxWidth(),
                rowName = stringResource(id = R.string.timer_setup_rest),
                defaultValue = "00:00",
                onButtonAdd = onRestAdd,
                onButtonRemove = onRestRemove
            )
        }
        item {
            TimerSetupStartButton(
                onClick = onStart
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
            onClick = { onButtonAdd() },
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
            onClick = { onButtonRemove() },
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
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
    ) {
        Text(text = stringResource(R.string.timer_setup_start_button))
    }
}