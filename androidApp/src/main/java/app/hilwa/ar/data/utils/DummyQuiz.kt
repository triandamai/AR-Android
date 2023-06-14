/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.data.utils

import app.hilwa.ar.R
import app.hilwa.ar.data.model.Quiz
import app.hilwa.ar.data.model.QuizQuestion

val dummyQuiz: List<Quiz> = listOf(
    Quiz(
        id = "001",
        quizImage = R.drawable.ic_quiz,
        quizTitle = "Mengenal bagian-bagian Otak Manusia",
        quizDescription = "Quiz yang mempelajari lebih lanjut apa saja bagian bagia pada otak manusia",
        quizDuration = "10 Menit",
        quizQuestionAmount = 10,
        progress=3,
        question = listOf(
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 4",
                    "Otak 3",
                    "Otak 2",
                    "Otak 1"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 5",
                    "Otak 4",
                    "Otak 3",
                    "Otak 2"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 1",
                    "Otak 6",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 1",
                    "Otak 7",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 10",
                    "Otak 5",
                    "Otak 2",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 3",
                    "Otak 8",
                    "Otak 7",
                    "Otak 4"
                )
            )
        )
    ),
    Quiz(
        id = "001",
        quizImage = R.drawable.ic_start_ar,
        quizTitle = "Bagian Otak Part I",
        quizDescription = "Quiz yang mempelajari lebih lanjut apa saja bagian bagia pada otak manusia",
        quizDuration = "10 Menit",
        quizQuestionAmount = 10,
        progress=1,
        question = listOf(
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 4",
                    "Otak 3",
                    "Otak 2",
                    "Otak 1"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 5",
                    "Otak 4",
                    "Otak 3",
                    "Otak 2"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 1",
                    "Otak 6",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 1",
                    "Otak 7",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 10",
                    "Otak 5",
                    "Otak 2",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 3",
                    "Otak 8",
                    "Otak 7",
                    "Otak 4"
                )
            )
        )
    ),
    Quiz(
        id = "001",
        quizImage = R.drawable.ic_onboard,
        quizTitle = "Bagian Otak Advanced",
        quizDescription = "Quiz yang mempelajari lebih lanjut apa saja bagian bagia pada otak manusia",
        quizDuration = "10 Menit",
        quizQuestionAmount = 10,
        progress=8,
        question = listOf(
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 4",
                    "Otak 3",
                    "Otak 2",
                    "Otak 1"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 5",
                    "Otak 4",
                    "Otak 3",
                    "Otak 2"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 1",
                    "Otak 6",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "001",
                question = "Apa nama bagian otak yang ada pada gambar tersebut?",
                image = R.drawable.ic_quiz,
                answer = listOf(
                    "Otak 1",
                    "Otak 7",
                    "Otak 3",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah gambar diatas terlihat seperti otak?",
                image = R.drawable.ic_start_ar,
                answer = listOf(
                    "Otak 10",
                    "Otak 5",
                    "Otak 2",
                    "Otak 4"
                )
            ),
            QuizQuestion(
                id = "002",
                question = "Apakah anda memiliki otak?",
                image = R.drawable.ic_about,
                answer = listOf(
                    "Otak 3",
                    "Otak 8",
                    "Otak 7",
                    "Otak 4"
                )
            )
        )
    )
)