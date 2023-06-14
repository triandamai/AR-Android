package app.hilwa.ar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import app.hilwa.ar.base.listener.AREventListener
import app.hilwa.ar.feature.auth.changePassword.ChangePassword
import app.hilwa.ar.feature.auth.changePassword.ChangePasswordViewModel
import app.hilwa.ar.feature.auth.changePassword.ScreenChangePassword
import app.hilwa.ar.feature.auth.onboard.Onboard
import app.hilwa.ar.feature.auth.onboard.OnboardState
import app.hilwa.ar.feature.auth.onboard.OnboardViewModel
import app.hilwa.ar.feature.auth.onboard.ScreenOnboard
import app.hilwa.ar.feature.auth.resetPassword.ResetPassword
import app.hilwa.ar.feature.auth.resetPassword.ResetPasswordViewModel
import app.hilwa.ar.feature.auth.resetPassword.ScreenResetPassword
import app.hilwa.ar.feature.auth.signin.ScreenSignIn
import app.hilwa.ar.feature.auth.signin.SignIn
import app.hilwa.ar.feature.auth.signin.SignInViewModel
import app.hilwa.ar.feature.auth.signup.ScreenSignUp
import app.hilwa.ar.feature.auth.signup.SignUp
import app.hilwa.ar.feature.auth.signup.SignUpViewModel
import app.hilwa.ar.feature.home.Home
import app.hilwa.ar.feature.home.HomeViewModel
import app.hilwa.ar.feature.home.ScreenHome
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuiz
import app.hilwa.ar.feature.quiz.detailQuiz.DetailQuizViewModel
import app.hilwa.ar.feature.quiz.detailQuiz.ScreenDetailQuiz
import app.hilwa.ar.feature.quiz.listQuiz.ListQuiz
import app.hilwa.ar.feature.quiz.listQuiz.ListQuizViewModel
import app.hilwa.ar.feature.quiz.listQuiz.ScreenListQuiz
import app.hilwa.ar.feature.quiz.startQuiz.ScreenStartQuiz
import app.hilwa.ar.feature.quiz.startQuiz.StartQuiz
import app.hilwa.ar.feature.quiz.startQuiz.StartQuizViewModel
import app.hilwa.ar.feature.splash.ScreenSplash
import app.hilwa.ar.feature.splash.Splash
import app.hilwa.ar.feature.splash.SplashViewModel
import app.trian.core.ui.UIController
import app.trian.core.ui.UIListener
import app.trian.core.ui.UIListenerData
import app.trian.core.ui.pageWrapper

@Composable
fun AppNavigation(
    uiController: UIController<AREventListener>
) {
    NavHost(
        navController = uiController.router,
        startDestination = Splash.routeName
    ) {
        pageWrapper<SplashViewModel,AREventListener>(
            route = Splash.routeName,
            controller = uiController
        ) {
            val uiState by this.uiState.collectAsState()
            val data by this.uiDataState.collectAsState()
            ScreenSplash(
                uiEvent = UIListenerData(
                    controller = uiController,
                    state = uiState,
                    data = data,
                    commit = this::commit,
                    commitData = this::commitData,
                    dispatcher = this::dispatch,
                )
            )
        }
        pageWrapper<SignInViewModel,AREventListener>(
            route = SignIn.routeName,
            controller = uiController
        ) {
            val uiState by uiState.collectAsState()
            ScreenSignIn(
                uiEvent = UIListener(
                    controller = uiController,
                    state = uiState,
                    commit = this::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<SignUpViewModel,AREventListener>(
            route = SignUp.routeName,
            controller = uiController
        ) {
            val uiState by uiState.collectAsState()
            ScreenSignUp(
                uiEvent = UIListener(
                    state = uiState,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<ResetPasswordViewModel,AREventListener>(
            route = ResetPassword.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            ScreenResetPassword(
                uiEvent = UIListener(
                    state = state,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<ChangePasswordViewModel,AREventListener>(
            route = ChangePassword.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            ScreenChangePassword(
                uiEvent = UIListener(
                    state = state,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<OnboardViewModel,AREventListener>(
            route = Onboard.routeName,
            controller = uiController
        ) {
            ScreenOnboard(
                uiEvent = UIListener(
                    state = OnboardState(),
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<HomeViewModel,AREventListener>(
            route = Home.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()

            ScreenHome(
                uiEvent = UIListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )

        }

        //quiz
        pageWrapper<ListQuizViewModel,AREventListener>(
            route = ListQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenListQuiz(
                uiEvent = UIListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<DetailQuizViewModel,AREventListener>(
            route = DetailQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenDetailQuiz(
                uiEvent = UIListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<StartQuizViewModel,AREventListener>(
            route = StartQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenStartQuiz(
                uiEvent = UIListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )
        }


    }
}