/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.lifecycleScope
import app.hilwa.ar.R
import app.hilwa.ar.data.model.ItemAR
import app.trian.mvi.ui.ResultStateData
import app.trian.mvi.ui.theme.ApplicationTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.google.ar.core.TrackingState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import io.github.sceneview.Scene
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale as SceneViewScale
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ArViewActivity : AppCompatActivity(R.layout.activity_ar_view) {
    val TAG = "ArViewActivity"

    lateinit var sceneView: ArSceneView
    lateinit var loading: LinearLayout
    lateinit var composeView: ComposeView

    private val viewModel: ArViewViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sceneView = findViewById(R.id.sceneView)
        loading = findViewById(R.id.ly_loading)
        composeView = findViewById(R.id.compose_view)

        loading.visibility = View.VISIBLE
        composeView.visibility = View.GONE

        lifecycleScope.launch {
            viewModel.items.collect {
                loading.visibility = View.GONE
                composeView.visibility = View.VISIBLE
                when (it) {
                    ResultStateData.Empty -> {

                    }

                    is ResultStateData.Error -> {}
                    ResultStateData.Loading -> {

                    }

                    is ResultStateData.Result -> {
                        setupInformationData(
                            visible = true,
                            item = it.data,
                            onClick = { _name, _location, _scale ->

                            }
                        )
                        try {
                            val bitmap = runBlocking(context = Dispatchers.IO) {
                                //it.data.image
                                Picasso.Builder(this@ArViewActivity).build().load("")
                                    .get()
                            }
                            sceneView.addChild(
                                AugmentedImageNode(
                                    imageName = it.data.type,
                                    bitmap = bitmap,
                                    onUpdate = { augImageNode, augImage ->
                                        Toast.makeText(
                                            this@ArViewActivity,
                                            "Detected ${augImage.name}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        when (augImage.trackingState) {
                                            TrackingState.TRACKING -> {
                                                loadModel(
                                                    augImageNode,
                                                    it.data.name,
                                                    it.data.glb,
                                                    it.data.scale
                                                )
                                            }

                                            TrackingState.PAUSED -> Unit
                                            TrackingState.STOPPED -> Unit
                                        }


                                    },
                                    onError = {
                                        Toast.makeText(
                                            this@ArViewActivity,
                                            it.message.orEmpty(),
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        Log.e(TAG, it.message.orEmpty())
                                    }
                                ).apply {
                                    loadModel(this, it.data.name, it.data.glb, it.data.scale)
                                }
                            )
                        } catch (e: Exception) {
                            //show error
                            Toast.makeText(
                                this@ArViewActivity,
                                e.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }
            }
        }
    }

    private fun loadModel(
        modelNode: ModelNode,
        name: String,
        location: String,
        scale: Float
    ) {
        modelNode.loadModelGlbAsync(
            glbFileLocation = location,
            onError = {
                Toast.makeText(
                    this@ArViewActivity,
                    it.message.orEmpty(),
                    Toast.LENGTH_LONG
                ).show()
            },
            onLoaded = { model ->
                Toast.makeText(
                    this@ArViewActivity,
                    "Model ${name} has been loaded ",
                    Toast.LENGTH_LONG
                ).show()
            }

        )
    }

    private fun loadModel(
        augmentedImageNode: AugmentedImageNode,
        name: String,
        location: String,
        scale: Float
    ) {
        augmentedImageNode.loadModelGlbAsync(
            glbFileLocation = location,
            autoAnimate = true,
            scaleToUnits = scale,
            onError = {
                Toast.makeText(
                    this@ArViewActivity,
                    it.message.orEmpty(),
                    Toast.LENGTH_LONG
                ).show()
            },
            onLoaded = { model ->
                Toast.makeText(
                    this@ArViewActivity,
                    "Model ${name} has been loaded ",
                    Toast.LENGTH_LONG
                ).show()
            }

        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    private fun setupInformationData(
        visible: Boolean = false,
        item: ItemAR,
        onClick: (String, String, Float) -> Unit = { _, _, _ -> }
    ) {
        Log.e("HEHEH", item.part.toString())
        composeView.setContent {
            var showDialog by remember {
                mutableStateOf(false)
            }
            val chipRounded = RoundedCornerShape(10.dp)
            val image = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(this@ArViewActivity)
                    .data(item.image)
                    .scale(Scale.FILL)
                    .build()
            )
            val node = listOf(
                ModelNode(
                    position = Position(x = 0.0f, y = 0.0f, z = -4.0f),
                    rotation = Rotation(y = 90.0f),
                    scale = SceneViewScale(0.5f),
                ).apply {
                    loadModel(
                        this,
                        "Model",
                        "models/corpus.glb",
                        0.6f
                    )

                }
            )

            if (showDialog) {
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(
                                fraction = 0.5f
                            )
                    ) {
                        Scene(
                            modifier = Modifier.fillMaxSize(),
                            nodes = node,
                            onCreate = { sceneView ->
                                // Apply your configuration
                            }
                        )
                    }
                }
            }
            ApplicationTheme {

                AnimatedVisibility(visible = visible) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(
                                    minHeight = 70.dp
                                )
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        ) {
                            Image(
                                painter = image,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(
                                        50.dp
                                    )
                                    .clip(MaterialTheme.shapes.medium)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Bagian-Bagian Otak",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                FlowRow {
                                    item.part.forEach {
                                        Row(
                                            modifier = Modifier
                                                .clip(chipRounded)
                                                .border(
                                                    width = 1.dp,
                                                    shape = chipRounded,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                .padding(
                                                    horizontal = 10.dp,
                                                    vertical = 6.dp
                                                )
                                                .clickable(
                                                    enabled = true,
                                                    onClick = {
                                                        showDialog = true
                                                        onClick(
                                                            it.value["name"] as String,
                                                            it.value["glb"] as String,
                                                            (it.value["scale"] as Double).toFloat()
                                                        )
                                                    }
                                                )

                                        ) {
                                            Text(
                                                text = it.value["name"] as String,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        //check camera permission


    }


    override fun onDestroy() {
        super.onDestroy()
    }
}