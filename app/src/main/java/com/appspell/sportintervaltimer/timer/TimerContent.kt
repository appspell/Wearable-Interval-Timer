package com.appspell.sportintervaltimer.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun TimerContent() {
    Scaffold(
        timeText = {
            TimeText()
        },
    ) {
        TimerCountDown()
    }
}

@Preview
@Composable
private fun TimerCountDown() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = 0.3f,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 4.dp,
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
                    .weight(0.3f)
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        text = "5 / 10",
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.caption3,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Work",
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.caption3,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.3f)
            ) {
                Text(
                    text = "00 : 00",
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.title2,
                    textAlign = TextAlign.Center,
                )
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