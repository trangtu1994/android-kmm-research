package com.example.simpletmdbapp2.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

open class BaseViewModel: ViewModel() {

    private val _pageState = MutableStateFlow(PageState(false))
    val pageState = _pageState.asStateFlow()

    fun launch(dispatcher: CoroutineDispatcher = Dispatchers.IO,
               onError: (Throwable) -> Unit = { e -> },
               onPrepare: () -> Unit = {
                   updateLoadingState(true)
               },
               finally: () -> Unit = {
                   updateLoadingState(false)
               },
               callable:suspend () -> Unit, ) {
        viewModelScope.launch (dispatcher) {
            onPrepare.invoke()
            try {
                callable.invoke()
            } catch (e: UnknownHostException) {
                PageState(false, ErrorDataState("No internet connection", "recheck your connection")).updatePageState()
            } catch(e: Exception) {
                onError.invoke(e)
            } finally {
                finally.invoke()
            }
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _pageState.value = _pageState.value.copy(loading = isLoading)
    }

    fun PageState.updatePageState() {
        _pageState.value = this.copy(
            loading =  this.loading,
            error = this.error
        )
    }
}