/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

import app.trian.mvi.ui.extensions.Empty
import com.google.errorprone.annotations.Keep

@Keep
data class ItemAR(
    val name: String = String.Empty,
    val description: String = String.Empty,
    val image: String = String.Empty,
    val glb: String = String.Empty,
    val type: String = String.Empty,
    val scale: Float = 1f,
    val part: Map<String, Map<String, Any>> = mapOf()
)
