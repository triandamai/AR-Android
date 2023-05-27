package app.trian.tudu

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import app.trian.tudu.feature.auth.changePassword.routeChangePassword
import app.trian.tudu.feature.auth.onboard.routeOnboard
import app.trian.tudu.feature.auth.resetPassword.routeResetPassword
import app.trian.tudu.feature.auth.signin.routeSignIn
import app.trian.tudu.feature.auth.signup.routeSignUp
import app.trian.tudu.feature.home.routeHome
import app.trian.tudu.feature.splash.Splash
import app.trian.tudu.feature.splash.routeSplash

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