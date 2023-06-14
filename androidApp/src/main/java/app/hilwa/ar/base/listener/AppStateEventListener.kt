/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.hilwa.ar.base.listener

import app.hilwa.ar.ApplicationStateConstants

fun interface AppStateEventListener {
    fun onEvent(event:ApplicationStateConstants)
}