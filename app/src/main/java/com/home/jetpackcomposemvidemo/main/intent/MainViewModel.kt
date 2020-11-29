package com.home.jetpackcomposemvidemo.main.intent

import androidx.hilt.lifecycle.ViewModelInject
import com.home.jetpackcomposemvidemo.common.BaseViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer
import io.reactivex.rxjava3.functions.BiFunction

class MainViewModel @ViewModelInject constructor(
        override val actionProcessor: MainProcessor
) : BaseViewModel<MainIntent, MainState, MainAction, MainResult>() {

    override fun initialState(): MainState = MainState.initialState()

    override fun intentFilter(): FlowableTransformer<MainIntent, MainIntent> {
        return FlowableTransformer { intents ->
            intents.publish { shared ->
                Flowable.merge<MainIntent>(
                        shared.ofType(MainIntent.InitialIntent::class.java).take(1),
                        shared.filter { it !is MainIntent.InitialIntent }
                )
            }
        }
    }

    override fun actionFromIntent(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.InitialIntent -> MainAction.InitAction
            is MainIntent.DataIntent -> MainAction.DataAction
        }
    }

    override fun reducer(): BiFunction<MainState, MainResult, MainState> =
            BiFunction { previousState, result ->
                when (result) {
                    is MainResult.InitResult -> previousState
                    is MainResult.DataResult.InProgress -> previousState.copy(
                            isLoading = true
                    )
                    is MainResult.DataResult.Success -> previousState.copy(
                            list = result.list,
                            error = null,
                            isLoading = false
                    )
                    is MainResult.DataResult.Failure -> previousState.copy(
                            error = result.error,
                            isLoading = false
                    )
                }
            }
}