package app.hilwa.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import app.hilwa.ar.base.BaseMainApp
import app.hilwa.ar.base.EventListener
import app.hilwa.ar.base.extensions.addOnEventListener
import app.hilwa.ar.base.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint

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

    private fun listen() {
        appState.addOnEventListener {
            if (it == "EXIT") {
                finish()
            }
        }
    }
}


