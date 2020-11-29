package com.home.jetpackcomposemvidemo.main.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import com.home.jetpackcomposemvidemo.main.intent.MainIntent
import com.home.jetpackcomposemvidemo.main.intent.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val liveData by lazy { viewModel.states() }
    private val dataPublishSubject: PublishSubject<MainIntent.DataIntent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { buildMainScreen(liveData, ::requestData) } }
        viewModel.processIntents(intents())
    }

    private fun requestData() {
        dataPublishSubject.onNext(MainIntent.DataIntent)
    }

    private fun intents(): Observable<MainIntent> {
        return Observable.merge(
                Observable.defer { Observable.just(MainIntent.InitialIntent) },
                dataPublishSubject.hide()
        )
    }
}