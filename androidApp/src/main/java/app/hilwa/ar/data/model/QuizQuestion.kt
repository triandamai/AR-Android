/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

data class QuizQuestion(
    val id:String,
    val question:String,
    val image:Int,
    val answer:List<String>
)
