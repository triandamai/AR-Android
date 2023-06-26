/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.arView

import android.os.Parcelable
import app.trian.mvi.ui.extensions.Empty
import io.github.sceneview.ar.node.ArNode
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.concurrent.Immutable

@Immutable
@Parcelize
data class ArViewState(
    val a: String = String.Empty,
    val nodes: @RawValue List<ArNode> = listOf()
) : Parcelable
