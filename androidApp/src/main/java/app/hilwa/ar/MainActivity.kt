package app.hilwa.ar

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.EventListener
import app.hilwa.ar.base.extensions.addOnEventListener
import app.hilwa.ar.base.extensions.formatTimer
import app.hilwa.ar.base.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var eventListener: EventListener
    private lateinit var appState: UIController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            eventListener = EventListener()
            appState = rememberUIController(
                event = eventListener
            )
            LaunchedEffect(
                key1 = null,
                block = {
                    appState.listenChanges()
                    listen()
                }
            )
            BaseMainApp(appState = appState) {
                AppNavigation(uiController = it)
            }
        }
    }

    private val durationInSecond = (5 * 60L)
    private val durationInMillis = TimeUnit.SECONDS.toMillis(durationInSecond)
    private val countDown = object : CountDownTimer(durationInMillis, 1000) {
        override fun onTick(millisUntilFinished: Long) {

            val formatString = millisUntilFinished.formatTimer()
            eventListener.updateTimer(false, formatString)
        }

        override fun onFinish() {
            eventListener.updateTimer(true)
        }

    }

    private fun listen() {
        appState.addOnEventListener {
            when (it) {
                ApplicationStateConstants.CANCEL_TIMER -> countDown.cancel()
                ApplicationStateConstants.EXIT_APP -> finish()
                ApplicationStateConstants.FINISH_TIMER -> countDown.onFinish()
                ApplicationStateConstants.START_TIMER -> countDown.start()
            }
        }
    }
}


