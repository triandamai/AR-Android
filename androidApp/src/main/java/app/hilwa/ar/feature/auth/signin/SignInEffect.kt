/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth.signin

import javax.annotation.concurrent.Immutable

@Immutable
sealed interface SignInEffect {
    object Nothing : SignInEffect
}