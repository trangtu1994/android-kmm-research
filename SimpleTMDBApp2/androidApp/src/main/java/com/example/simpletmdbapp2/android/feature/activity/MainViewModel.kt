package com.example.simpletmdbapp2.android.feature.activity

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletmdbapp2.android.base.BaseViewModel
import com.example.simpletmdbapp2.android.base.ErrorDataState
import com.example.simpletmdbapp2.android.base.PageState
import com.example.simpletmdbapp2.android.helper.CONFIG_TIME_CACHE_MIL_SEC
import com.example.simpletmdbapp2.android.helper.LAST_CACHE_LIST_TIME
import com.example.simpletmdbapp2.android.helper.SearchRoute
import com.example.simpletmdbapp2.android.helper.TrendingRoute
import com.example.simpletmdbapp2.android.helper.appNavRoutes
import com.example.simpletmdbapp2.data.SharedPreferenceRepository
import com.example.simpletmdbapp2.kotinext.safe
import com.example.simpletmdbapp2.sdk.ErrorResult
import com.example.simpletmdbapp2.sdk.SuccessResult
import com.example.simpletmdbapp2.sdk.TMDBSimpleSDK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(
    private val sdk: TMDBSimpleSDK,
    private val preferenceRepository: SharedPreferenceRepository,
) : BaseViewModel() {

    private val _state = mutableStateOf(MainModelState())
    val state: State<MainModelState> = _state

    init {
        fetchCommonConfigurations()
    }

    private fun fetchCommonConfigurations() {
        launch {
            sdk.initSdkConfiguration(viewModelScope) { result ->
                when (result) {
                    is SuccessResult<*> -> {
                        initLaunch()
                    }
                    else -> {
                        Log.i("⏭️ ERROR", "FAIL loading config")
                        PageState(false, ErrorDataState("Fail loading config", "Recheck connection!")).updatePageState()
                    }
                }
            }
        }
    }

    private fun initLaunch() {
        searchMovies("", checkShouldReload())
    }

    fun searchMovies(term: String, forceLoad: Boolean = false) {
        updateListState { MainModelState(searching = term) }
        launch(onError = {
            _state.value = _state.value.copy(
                isSuccessful = false,
                pageSize = 0,
                launches = emptyList()
            )
            PageState(false, ErrorDataState(it.message ?: "Unknown error")).updatePageState()
        }) {
            val isForceLoad = forceLoad || checkShouldReload() || term.isNullOrBlank()
            val pageMovie = sdk.getFavoriteMovies(term, isForceLoad)
            updateListState {
                MainModelState(
                    pageMovie.page.safe() > 0,
                    10,
                    pageMovie.result,
                    searching = term
                )
            }
            PageState(false, ErrorDataState("Not found data match criteria!").takeIf { pageMovie.result.isEmpty() }).updatePageState()
            if (isForceLoad) {
                saveCacheTime()
            }
        }
    }


    fun onRefresh() {
        updateSearchTerm("")
        searchMovies("", true)
    }

    fun updateSearchTerm(term: String) {
        updateListState { _state.value.copy(searching = term) }
    }

    private fun checkShouldReload() : Boolean {
        val lastLong = preferenceRepository.getLong(LAST_CACHE_LIST_TIME)
        val date = if (lastLong > 0) Date() else null
        return date == null || (date.time - lastLong) > CONFIG_TIME_CACHE_MIL_SEC
    }

    private fun saveCacheTime() {
        preferenceRepository.saveLong(LAST_CACHE_LIST_TIME, Date().time)
    }

    private fun updateListState(update: () -> MainModelState) {
        val newState = update.invoke()
        _state.value = _state.value.copy (
            pageSize = newState.pageSize,
            launches = newState.launches,
            searching = newState.searching
        )
    }

    fun navRouteTile(id: String?): String {
        val fPath = id?.split("/")?.firstOrNull().safe()
        if (fPath == TrendingRoute.id) {
            return SearchRoute.takeIf { _state.value.searching.safe().isNotBlank() }?.title ?: TrendingRoute.title
        }
        return appNavRoutes.firstOrNull { it.id == fPath }?.title ?: TrendingRoute.title
    }
}