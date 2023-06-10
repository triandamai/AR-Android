package app.hilwa.ar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import app.hilwa.ar.base.UIWrapperListener
import app.hilwa.ar.base.UIWrapperListenerData
import app.hilwa.ar.base.pageWrapper
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

@Composable
fun AppNavigation(
    uiController: UIController
) {
    NavHost(
        navController = uiController.router,
        startDestination = Splash.routeName
    ) {
        pageWrapper<SplashViewModel>(
            route = Splash.routeName,
            controller = uiController
        ) {
            val uiState by this.uiState.collectAsState()
            val data by this.uiDataState.collectAsState()
            ScreenSplash(
                invoker = UIWrapperListenerData(
                    controller = uiController,
                    state = uiState,
                    data = data,
                    commit = this::commit,
                    commitData = this::commitData,
                    dispatcher = this::dispatch,
                )
            )
        }
        pageWrapper<SignInViewModel>(
            route = SignIn.routeName,
            controller = uiController
        ) {
            val uiState by uiState.collectAsState()
            ScreenSignIn(
                state = uiState,
                invoker = UIWrapperListener(
                    controller = uiController,
                    state = uiState,
                    commit = this::commit,
                )
            )
        }

        pageWrapper<SignUpViewModel>(
            route = SignUp.routeName,
            controller = uiController
        ) {
            val uiState by uiState.collectAsState()
            ScreenSignUp(
                state = uiState,
                invoker = UIWrapperListener(
                    state = uiState,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<ResetPasswordViewModel>(
            route = ResetPassword.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            ScreenResetPassword(
                state = state,
                invoker = UIWrapperListener(
                    state = state,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<ChangePasswordViewModel>(
            route = ChangePassword.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            ScreenChangePassword(
                state = state,
                invoker = UIWrapperListener(
                    state = state,
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<OnboardViewModel>(
            route = Onboard.routeName,
            controller = uiController
        ) {
            ScreenOnboard(
                invoker = UIWrapperListener(
                    state = OnboardState(),
                    controller = uiController,
                    commit = ::commit,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<HomeViewModel>(
            route = Home.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()

            ScreenHome(
                invoker = UIWrapperListenerData(
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
        pageWrapper<ListQuizViewModel>(
            route = ListQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenListQuiz(
                state = state,
                data = dataState,
                invoker = UIWrapperListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<DetailQuizViewModel>(
            route = DetailQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenDetailQuiz(
                state = state,
                data = dataState,
                invoker = UIWrapperListenerData(
                    controller = uiController,
                    state = state,
                    data = dataState,
                    commit = ::commit,
                    commitData = ::commitData,
                    dispatcher = ::dispatch
                )
            )
        }

        pageWrapper<StartQuizViewModel>(
            route = StartQuiz.routeName,
            controller = uiController
        ) {
            val state by uiState.collectAsState()
            val dataState by uiDataState.collectAsState()
            ScreenStartQuiz(
                state = state,
                data = dataState,
                invoker = UIWrapperListenerData(
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