package app.hilwa.ar

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import app.hilwa.ar.feature.auth.changePassword.routeChangePassword
import app.hilwa.ar.feature.auth.onboard.routeOnboard
import app.hilwa.ar.feature.auth.resetPassword.routeResetPassword
import app.hilwa.ar.feature.auth.signin.routeSignIn
import app.hilwa.ar.feature.auth.signup.routeSignUp
import app.hilwa.ar.feature.home.routeHome
import app.hilwa.ar.feature.splash.Splash
import app.hilwa.ar.feature.splash.routeSplash

@Composable
fun AppNavigation(
    applicationState: ApplicationState
) {
    NavHost(
        navController = applicationState.router,
        startDestination = Splash.routeName
    ) {
        routeSplash(applicationState)

        routeSignIn(applicationState)

        routeSignUp(applicationState)

        routeChangePassword(applicationState)

        routeResetPassword(applicationState)

        routeOnboard(applicationState)

        routeHome(applicationState)


    }
}