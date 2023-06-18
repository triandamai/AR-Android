package app.hilwa.ar

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import app.hilwa.ar.feature.splash.Splash
import app.trian.ksp.androidAppComponent
import app.trian.mvi.ui.BaseMainApp
import app.trian.mvi.ui.UIController
import app.trian.mvi.ui.extensions.formatTimer
import app.trian.mvi.ui.listener.BaseEventListener
import app.trian.mvi.ui.listener.EventListener
import app.trian.mvi.ui.rememberUIController
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
            BaseMainApp(uiController) {
                NavHost(
                    navController = uiController.router,
                    startDestination = Splash.routeName
                ) {
                    androidAppComponent(it)
                }
            }
        }
    }

    private val durationInSecond = (5 * 60L)
    private val durationInMillis = TimeUnit.SECONDS.toMillis(durationInSecond)
    private val countDown = object : CountDownTimer(durationInMillis, 1000) {
        override fun onTick(millisUntilFinished: Long) {

            val formatString = millisUntilFinished.formatTimer()
            eventListener.sendEventToScreen("updateTimer", "0", formatString)
        }

        override fun onFinish() {
            eventListener.sendEventToScreen("updateTimer", "1", "")

        }

    }

    private fun listen() {
        uiController.event.addOnAppEventListener { event, params ->
            when (event) {
                "CANCEL_TIMER" -> countDown.cancel()
                "EXIT" -> finish()
                "FINISH_TIMER" -> countDown.onFinish()
                "START_TIMER" -> countDown.start()
            }
        }
    }
}


