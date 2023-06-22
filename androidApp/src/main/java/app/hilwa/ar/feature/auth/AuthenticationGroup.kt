/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.auth

import app.hilwa.ar.feature.auth.onboard.Onboard
import app.trian.mvi.NavigationGroup

object Authentication{
    const val routeName ="Auth"
}
@NavigationGroup(
    route = Authentication.routeName,
    startDestination = Onboard.routeName
)
interface AuthenticationGroup