package com.home.jetpackcomposemvidemo.common

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable

interface MviViewModel<I : MviIntent, S : MviState> {

    fun processIntents(intents: Observable<I>)

    fun states(): LiveData<S>
}