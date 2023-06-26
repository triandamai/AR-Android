/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.arView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import app.trian.mvi.Navigation
import app.trian.mvi.ui.BaseScreen
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import com.gorisse.thomas.lifecycle.lifecycleScope
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.math.Position
import kotlinx.coroutines.launch

object ArView {
    const val routeName = "ArView"
}

@Navigation(
    route = ArView.routeName,
    viewModel = ArViewViewModel::class
)
@Composable
fun ArViewScreen(
    uiContract: UIContract<ArViewState, ArViewIntent, ArViewAction>,
    event: BaseEventListener = EventListener()
) = UIWrapper(uiContract = uiContract) {


    val modelNode = ArModelNode()
    LaunchedEffect(key1 = Unit, block = {
        modelNode.loadModelGlbAsync(
            glbFileLocation = "models/brain.glb",
            autoAnimate = true,
            centerOrigin = Position(x = 0.0f, y = 0.0f, z = 0.0f),
            onError = {

            },
            scaleToUnits = 0.5f
        ) {

        }
    })

    BaseScreen {
        Box(modifier = Modifier.fillMaxSize()) {
            ARScene(
                modifier = Modifier.fillMaxSize(),
                nodes = state.nodes,
                planeRenderer = true,
                onCreate = {
                    it.addChild(modelNode)
                },
                onSessionCreate = {
                    modelNode.anchor()
                    this.planeRenderer.isVisible = false
                },
                onFrame = {

                },
                onTap = {

                }
            )
        }
    }

}