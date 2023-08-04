package app.hilwa.ar

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import app.hilwa.ar.feature.quiz.startQuiz.StartQuiz
import app.hilwa.ar.feature.splash.Splash
import app.trian.mvi.androidAppComponent
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.extensions.formatTimer
import app.trian.mvi.ui.internal.UIController
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.darkColors
import app.trian.mvi.ui.theme.lightColors
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var eventListener: BaseEventListener
    private lateinit var uiController: UIController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            eventListener = EventListener()
            uiController = rememberUIController(
                event = eventListener
            )
            LaunchedEffect(
                key1 = null,
                block = {
                    listen()
                }
            )
            BaseMainApp(
                controller = uiController,
                lightColor = lightColors,
                darkColor = lightColors
            ) {
                NavHost(
                    navController = uiController.navigator.navHost,
                    startDestination = Splash.routeName
                ) {
                    androidAppComponent(it, eventListener)
                }
            }
        }
    }

    private val durationInSecond = (5 * 60L)
    private val durationInMillis = TimeUnit.SECONDS.toMillis(durationInSecond)
    private val countDown = object : CountDownTimer(durationInMillis, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val formatString = millisUntilFinished.formatTimer()
            eventListener.sendEventToScreen("", formatString)
        }

        override fun onFinish() {
            eventListener.sendEventToScreen(StartQuiz.Timeout)
        }
    }

    private fun listen() {
        eventListener.addOnAppEventListener { event, params ->
            when (event) {
                "CANCEL_TIMER" -> countDown.cancel()
                "EXIT" -> finish()
                "FINISH_TIMER" -> countDown.onFinish()
                "START_TIMER" -> countDown.start()
            }
        }
    }
}


