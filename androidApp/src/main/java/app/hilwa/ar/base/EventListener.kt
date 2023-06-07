/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.hilwa.ar.base

import androidx.compose.material.ModalBottomSheetValue
import app.hilwa.ar.ApplicationStateConstants
import app.hilwa.ar.base.listener.AppStateEventListener
import app.hilwa.ar.base.listener.BottomSheetStateListener
import app.hilwa.ar.base.listener.CountDownTimerListener


class EventListener {
    private var appEvent: AppStateEventListener? = null
    private var bottomSheetStateListener: BottomSheetStateListener? = null
    private var countDownTimerListener:CountDownTimerListener?=null

    //timer
    fun addTimerListener(listener: CountDownTimerListener){
        countDownTimerListener=listener
    }
    fun updateTimer(isTimeout:Boolean,vararg value:String){
        countDownTimerListener?.onNotify(isTimeout,*value)
    }
    //end timer
    //region app event
    fun addOnEventListener(listener: AppStateEventListener) {
        appEvent = listener
    }

    fun sendEvent(eventName: ApplicationStateConstants) {
        appEvent?.onEvent(eventName)
    }
    //end region

    //region bottom sheet
    fun addOnBottomSheetStateListener(listener: BottomSheetStateListener) {
        bottomSheetStateListener = listener
    }

    fun changeBottomSheetState(state: ModalBottomSheetValue) {
        bottomSheetStateListener?.onStateChanges(state)
    }
    //end region

    fun clear() {
        appEvent = null
        bottomSheetStateListener = null
    }


}
