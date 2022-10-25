package com.appspell.sportintervaltimer

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.appspell.sportintervaltimer.Navigation.Finish
import com.appspell.sportintervaltimer.Navigation.Timer
import com.appspell.sportintervaltimer.Navigation.TimerSetup
import com.appspell.sportintervaltimer.finish.FinishContent
import com.appspell.sportintervaltimer.timer.TimerContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            val navController = rememberSwipeDismissableNavController()
            SwipeDismissableNavHost(
                navController = navController,
                startDestination = TimerSetup.route
            ) {
                composable(TimerSetup.route) {
                    TimerSetupContent(
                        navController = navController
                    )
                }
                composable(Timer.route) {
                    TimerContent(
                        navController = navController
                    )
                }
                composable(Finish.route) {
                    FinishContent(
                        navController = navController
                    )
                }
            }
        }
    }
}