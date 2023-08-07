/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.signup

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface SignUpEffect {
    object Nothing : SignUpEffect

}