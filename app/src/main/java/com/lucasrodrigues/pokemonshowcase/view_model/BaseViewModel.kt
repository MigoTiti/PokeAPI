package com.lucasrodrigues.pokemonshowcase.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucasrodrigues.pokemonshowcase.framework.AlertService
import com.lucasrodrigues.pokemonshowcase.framework.NavigationService
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    protected val navigationService: NavigationService,
    protected val alertService: AlertService,
) : ViewModel() {

    fun request(
        onRequestBegin: () -> Unit = {},
        onError: (error: Throwable) -> Unit = {},
        onRequestSuccess: () -> Unit = {},
        showErrorAlert: Boolean = true,
        loadingLiveData: MutableLiveData<LoadingState>? = null,
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                onRequestBegin()
                loadingLiveData?.postValue(LoadingState.Loading)

                action()

                onRequestSuccess()
                loadingLiveData?.postValue(LoadingState.Idle)
            } catch (e: Throwable) {
                onError(e)
                loadingLiveData?.postValue(LoadingState.Error(e))

                handleError(e, showErrorAlert)
            }
        }
    }

    fun <T>request(
        onRequestBegin: () -> Unit = {},
        onRequestSuccess: (result: T) -> Unit = {},
        onError: (error: Throwable) -> Unit = {},
        showErrorAlert: Boolean = true,
        loadingLiveData: MutableLiveData<LoadingState>? = null,
        resultLiveData: MutableLiveData<T>? = null,
        action: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                onRequestBegin()
                loadingLiveData?.postValue(LoadingState.Loading)

                val result = action()

                onRequestSuccess(result)

                resultLiveData?.postValue(result)
                loadingLiveData?.postValue(LoadingState.Idle)
            } catch (e: Throwable) {
                onError(e)
                loadingLiveData?.postValue(LoadingState.Error(e))

                handleError(e, showErrorAlert)
            }
        }
    }

    private fun handleError(error: Throwable, showErrorAlert: Boolean) {
        error.printStackTrace()

        when {
            showErrorAlert -> {
                alertService.sendErrorAlert(error)
            }
        }
    }
}
