/*
 * Copyright (c) 2023 trian.app.
 *
 *  Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *
 */

package app.hilwa.ar.feature.augmentedReality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.hilwa.ar.data.domain.ar.GetArDataUseCase
import app.hilwa.ar.data.model.ItemAR
import app.trian.mvi.ui.ResultStateData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArViewViewModel @Inject constructor(
    private val getArDataUseCase: GetArDataUseCase,
) : ViewModel() {
    private val _items: MutableStateFlow<ResultStateData<ItemAR>> =
        MutableStateFlow(ResultStateData.Loading)
    val items get() = _items.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading get() = _loading.asStateFlow()

    init {
        getListInformation()
    }

    private fun getListInformation() = viewModelScope.launch {
        _loading.tryEmit(true)
        getArDataUseCase("wy82dCzeBhFStp1S59wA")
            .collect {
                _loading.tryEmit(false)
                _items.tryEmit(it)
            }
    }
}