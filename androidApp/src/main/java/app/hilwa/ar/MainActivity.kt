package app.hilwa.ar

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.EventListener
import app.hilwa.ar.base.extensions.addOnEventListener
import app.hilwa.ar.base.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var eventListener: EventListener
    private lateinit var appState: ApplicationState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            eventListener = EventListener()
            appState = rememberApplicationState(
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
                AppNavigation(applicationState = it)
            }
        }
    }

    private val durationInSecond = (60 * 60L)
    private val durationInMillis = TimeUnit.SECONDS.toMillis(durationInSecond)
    private val countDown = object : CountDownTimer(durationInMillis, 1000) {
        override fun onTick(millisUntilFinished: Long) {

            val formatString = String.format(
                "%02d : %02d : %02d ",
                TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                millisUntilFinished
                            )
                        )
            )
            eventListener.updateTimer(
                false,
                formatString
            )
        }

        override fun onFinish() {
            eventListener.updateTimer(
                true
            )
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


