/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.base.listener

import app.hilwa.ar.ApplicationStateConstants
import app.trian.core.ui.listener.BaseEventListener

class AREventListener : BaseEventListener() {
    private var countDownTimerListener: CountDownTimerListener? = null
    private var arEventListener: AppStateEventListener? = null

    fun addOnCountDownListener(listener: CountDownTimerListener) {
        countDownTimerListener = listener
    }

    fun updateTimer(isTimeout: Boolean, vararg value: String) {
        countDownTimerListener?.onNotify(isTimeout, *value)
    }

    fun addOnArAppEventListener(listener: AppStateEventListener) {
        arEventListener = listener
    }

    fun sendEvent(event: ApplicationStateConstants) {
        arEventListener?.onEvent(event)
    }


}