package com.home.jetpackcomposemvidemo.main.intent

import com.home.jetpackcomposemvidemo.main.model.MainDataBean
import com.home.jetpackcomposemvidemo.common.MviState

data class MainState(
        val list: List<MainDataBean> = listOf(),
        val error: Throwable? = null,
        val isLoading: Boolean = false
) : MviState {
    companion object {
        fun initialState(): MainState = MainState()
    }
}

fun MainState.isLoading() = isLoading

fun MainState.onResponse() = list.isNotEmpty()

fun MainState.onFailure() = error != null