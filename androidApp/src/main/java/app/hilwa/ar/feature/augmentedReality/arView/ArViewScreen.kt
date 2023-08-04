/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality.arView

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import app.trian.mvi.Navigation
import app.trian.mvi.ui.UIWrapper
import app.trian.mvi.ui.internal.UIContract
import app.trian.mvi.ui.internal.listener.BaseEventListener
import app.trian.mvi.ui.internal.listener.EventListener
import app.trian.mvi.ui.internal.rememberUIController
import app.trian.mvi.ui.theme.ApplicationTheme

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


//    val modelNode = ArModelNode()
//    LaunchedEffect(key1 = Unit, block = {
//        modelNode.loadModelGlbAsync(
//            glbFileLocation = "models/brain.glb",
//            autoAnimate = true,
//            centerOrigin = Position(x = 0.0f, y = 0.0f, z = 0.0f),
//            onError = {
//
//            },
//            scaleToUnits = 0.7f
//        ) {
//
//        }
//    })
//
//    BaseScreen {
//        Box(modifier = Modifier.fillMaxSize()) {
//            ARScene(
//                modifier = Modifier.fillMaxSize(),
//                nodes = state.nodes,
//                planeRenderer = true,
//                onCreate = {
//                    it.addChild(modelNode)
//                },
//                onSessionCreate = {
//                    modelNode.anchor()
//                    this.planeRenderer.isVisible = false
//                },
//                onFrame = {
//
//                },
//                onTap = {
//
//                }
//            )
//        }
//    }

}

@Preview
@Composable
fun PreviewArViewScreen() {
    ApplicationTheme {

        ArViewScreen(
            uiContract = UIContract(
                controller = rememberUIController(),
                state = ArViewState()
            )
        )
    }
}