/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  
 */

package app.hilwa.ar

sealed interface  ApplicationStateConstants {
    object EXIT_APP:ApplicationStateConstants
    object START_TIMER:ApplicationStateConstants
    object CANCEL_TIMER:ApplicationStateConstants
    object FINISH_TIMER:ApplicationStateConstants

}

