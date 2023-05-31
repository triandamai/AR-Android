/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.model

import app.hilwa.ar.R

data class Quiz(
    val id:String="",
    val quizImage:Int= R.drawable.ic_start_ar,
    val quizTitle:String="",
    val quizDescription:String="",
    val quizDuration:String="",
    val quizQuestionAmount:Int=0,
    val progress:Int=0,
    val question:List<QuizQuestion> = listOf()
)
