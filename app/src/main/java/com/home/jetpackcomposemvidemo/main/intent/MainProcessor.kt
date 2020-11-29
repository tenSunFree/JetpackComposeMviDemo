package com.home.jetpackcomposemvidemo.main.intent

import com.home.jetpackcomposemvidemo.common.ISchedulerProvider
import com.home.jetpackcomposemvidemo.common.MviActionProcessor
import com.home.jetpackcomposemvidemo.main.model.IMainRepository
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableTransformer

open class MainProcessor(
        private val repository: IMainRepository,
        override val schedulerProvider: ISchedulerProvider
) : MviActionProcessor<MainAction, MainResult> {

    override fun transformFromAction(): FlowableTransformer<MainAction, MainResult> =
            FlowableTransformer { actionFlowable ->
                actionFlowable.publish { shared ->
                    Flowable.merge(
                            shared.ofType(MainAction.InitAction::class.java).compose(initUi()),
                            shared.ofType(MainAction.DataAction::class.java).compose(getData())
                    )
                }
            }

    private fun initUi(): FlowableTransformer<MainAction.InitAction, MainResult.InitResult> =
            FlowableTransformer { it.switchMap { Flowable.just(MainResult.InitResult) } }

    private fun getData(): FlowableTransformer<MainAction.DataAction, MainResult.DataResult> =
            FlowableTransformer { actionFlowable ->
                actionFlowable.switchMap { action ->
                    // Loggerg.d("MovieProcessor, searchMovies")
                    repository.getData()
                            .map {
                                MainResult.DataResult.Success(
                                    list = it,
                                        // query = action.query
                                )
                            }
                            .cast(MainResult.DataResult::class.java)
                            .onErrorReturn { error ->
                                MainResult.DataResult.Failure(
                                        error = error,
                                        // query = action.query
                                )
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWithItem(
                                    MainResult.DataResult.InProgress
                            )
                }
            }
}