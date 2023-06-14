package app.hilwa.ar

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import app.hilwa.ar.base.listener.AREventListener
import app.trian.core.ui.BaseMainApp
import app.trian.core.ui.UIController
import app.trian.core.ui.extensions.formatTimer
import app.trian.core.ui.rememberUIController
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var eventListener: AREventListener
    private lateinit var uiController: UIController<AREventListener>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            eventListener = AREventListener()
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
        uiController.event.addOnArAppEventListener{
            when (it) {
                ApplicationStateConstants.CANCEL_TIMER -> countDown.cancel()
                ApplicationStateConstants.EXIT_APP -> finish()
                ApplicationStateConstants.FINISH_TIMER -> countDown.onFinish()
                ApplicationStateConstants.START_TIMER -> countDown.start()
            }
        }
    }
}


