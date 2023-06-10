/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package app.hilwa.ar.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import app.hilwa.ar.UIController
import app.hilwa.ar.base.extensions.addOnBottomSheetStateChangeListener
import app.hilwa.ar.base.extensions.backPressedAndClose
import app.hilwa.ar.base.extensions.hideBottomSheet
import app.hilwa.ar.base.extensions.hideKeyboard
import app.hilwa.ar.base.extensions.navigate
import app.hilwa.ar.base.extensions.navigateAndReplace
import app.hilwa.ar.base.extensions.navigateAndReplaceAll
import app.hilwa.ar.base.extensions.navigateSingleTop
import app.hilwa.ar.base.extensions.navigateUp
import app.hilwa.ar.base.extensions.showBottomSheet
import app.hilwa.ar.base.listener.BottomSheetStateListener


inline fun <reified VM : BaseViewModel<*, *>> NavGraphBuilder.pageWrapper(
    route: String,
    controller: UIController,
    parent: String? = null,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    crossinline content: @Composable VM.() -> Unit = {}
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks
    ) {
        val vm = (if (parent.isNullOrEmpty()) {
            hiltViewModel()
        } else {
            val parentEntry = remember(controller.router.currentBackStackEntry) {
                controller
                    .router
                    .getBackStackEntry(parent)
            }
            hiltViewModel<VM>(parentEntry)
        }).also { it.setAppState(controller) }
        content(vm)
    }
}

@Composable
inline fun <reified VM : BaseViewModel<*, *>> PageWrapper(
    appState: UIController,
    parent: String? = null,
    content: @Composable VM.() -> Unit = {}
) {
    val vm = (if (parent.isNullOrEmpty()) {
        hiltViewModel()
    } else {
        val parentEntry = remember(appState.router.currentBackStackEntry) {
            appState
                .router
                .getBackStackEntry(parent)
        }
        hiltViewModel<VM>(parentEntry)
    }).also { it.setAppState(appState) }
    content(vm)
}

open class UIWrapperListener<State, Event>(
    val controller: UIController,
    private val state: State,
    private val commit: (State) -> Unit = {},
    private val dispatcher: (Event) -> Unit = {},
) {
    fun commit(s: State.() -> State) {
        this.commit(s(state))
    }

    fun dispatch(e: Event) {
        this.dispatcher(e)
    }

    //application state nav
    //region navigation
    fun backAndClose() =
        controller.backPressedAndClose()

    fun navigateUp() =
        controller.navigateUp()

    fun navigate(routeName: String, vararg args: String) =
        controller.navigate(routeName, *args)

    fun navigateSingleTop(routeName: String, vararg args: String) =
        controller.navigateSingleTop(routeName, *args)

    fun navigateSingleTop(routeName: String) =
        controller.navigateSingleTop(routeName)

    fun navigateAndReplace(routeName: String, vararg args: String) =
        controller.navigateAndReplace(routeName, *args)

    fun navigateAndReplaceAll(routeName: String, vararg args: String) =
        controller.navigateAndReplaceAll(routeName, *args)

    fun navigateAndReplaceAll(routeName: String) =
        controller.navigateAndReplaceAll(routeName)

    //end region
    //region bottomsheet
    fun hideBottomSheet() =
        controller.hideBottomSheet()

    fun showBottomSheet() =
        controller.showBottomSheet()

    fun addOnBottomSheetStateChangeListener(listener: BottomSheetStateListener) =
        controller.addOnBottomSheetStateChangeListener(listener)
    //end region

    //keyboard
    fun hideKeyboard() =
        controller.context.hideKeyboard()
    //end region

    //snackbar
    //region snakcbar

    fun showSnackbar(message: String) =
        controller.showSnackbar(message)
    fun showSnackbar(message: Int) =
        controller.showSnackbar(message)
    fun showSnackbar(message: Int, vararg params: String) =
        controller.showSnackbar(message, *params)
    //

    //end region
    //end
}

class UIWrapperListenerData<State, Data, Event>(
    controller: UIController,
    state: State,
    private val data: Data,
    commit: (State) -> Unit = {},
    private val commitData: (Data) -> Unit = {},
    dispatcher: (Event) -> Unit = {},
) : UIWrapperListener<State, Event>(
    controller = controller,
    state = state,
    commit = commit,
    dispatcher = dispatcher
) {
    fun commitData(d: Data.() -> Data) {
        this.commitData(d(data))
    }
}

@Composable
inline fun <State, Event> UIWrapper(
    invoker: UIWrapperListener<State, Event>,
    content: UIWrapperListener<State, Event>.() -> Unit
) {
    content(invoker)
}

@Composable
inline fun <State, Data, Event> UiWrapperData(
    invoker: UIWrapperListenerData<State, Data, Event>,
    content: UIWrapperListenerData<State, Data, Event>.() -> Unit
) {
    content(invoker)
}
