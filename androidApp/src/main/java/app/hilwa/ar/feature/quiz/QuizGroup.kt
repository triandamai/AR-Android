/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.quiz

import app.hilwa.ar.feature.quiz.listQuiz.ListQuiz
import app.trian.mvi.NavigationGroup

object Quiz {
    const val routeName = "Quiz"
}

@NavigationGroup(
    route = Quiz.routeName,
    startDestination = ListQuiz.routeName
)
interface QuizGroup {
}