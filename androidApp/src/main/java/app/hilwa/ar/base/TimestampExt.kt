/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.base

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun Timestamp.format(pattern: String = "dd-MM-yyy"): String {
    val date = this.toDate()
    return SimpleDateFormat(pattern, Locale("ID")).format(date)
}