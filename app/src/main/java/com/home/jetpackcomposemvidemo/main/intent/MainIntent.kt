package com.home.jetpackcomposemvidemo.main.intent

import com.home.jetpackcomposemvidemo.common.MviIntent

sealed class MainIntent : MviIntent {

    object InitialIntent : MainIntent()

    object DataIntent : MainIntent()
}