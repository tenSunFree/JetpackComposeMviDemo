package com.home.jetpackcomposemvidemo.main.intent

import com.home.jetpackcomposemvidemo.common.MviAction

sealed class MainAction : MviAction {

    object InitAction : MainAction()

    object DataAction : MainAction()
}