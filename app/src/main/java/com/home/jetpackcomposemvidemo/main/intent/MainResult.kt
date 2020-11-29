package com.home.jetpackcomposemvidemo.main.intent

import com.home.jetpackcomposemvidemo.main.model.MainDataBean
import com.home.jetpackcomposemvidemo.common.MviResult

sealed class MainResult : MviResult {

    object InitResult : MainResult()

    sealed class DataResult : MainResult() {
        data class Success(val list: List<MainDataBean>) : DataResult()
        data class Failure(val error: Throwable) : DataResult()
        object InProgress : DataResult()
    }
}