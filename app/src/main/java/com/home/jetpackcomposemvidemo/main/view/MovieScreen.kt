package com.home.jetpackcomposemvidemo.main.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.LottieAnimationState
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.home.jetpackcomposemvidemo.main.model.MainDataBean
import com.home.jetpackcomposemvidemo.R
import com.home.jetpackcomposemvidemo.main.intent.MainState
import com.home.jetpackcomposemvidemo.main.intent.isLoading
import com.home.jetpackcomposemvidemo.main.intent.onFailure
import com.home.jetpackcomposemvidemo.main.intent.onResponse

@Composable
fun buildMainScreen(stateLiveData: LiveData<MainState>, requestData: () -> Unit) {
    val isRunWelcome = remember { mutableStateOf(true) }
    val isFirstRequestData = remember { mutableStateOf(true) }
    if (isRunWelcome.value) {
        buildWelcomeWeight(isRunWelcome)
    } else {
        val moviesState: MainState by stateLiveData.observeAsState(MainState.initialState())
        Column {
            when {
                moviesState.isLoading() -> buildLoadingWeight()
                moviesState.onResponse() -> buildListWeight(moviesState.list)
                moviesState.onFailure() -> buildErrorWeight(moviesState.error!!)
            }
        }
        if (isFirstRequestData.value) {
            requestData()
            isFirstRequestData.value = false
        }
    }
}

@Composable
fun buildWelcomeWeight(
        isRunWelcome: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val animationSpec = remember {
        LottieAnimationSpec.RawRes(R.raw.welcome)
    }
    val animationState: LottieAnimationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = 0)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val reference = createRef()
        LottieAnimation(
                animationSpec,
                modifier = Modifier.preferredSize(100.dp).constrainAs(reference) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                animationState = animationState
        )
    }
    isRunWelcome.value = animationState.progress < 1f
}

@Composable
fun buildLoadingWeight() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val reference = createRef()
        CircularProgressIndicator(
                modifier = Modifier.constrainAs(reference) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

    }
}

@Composable
fun buildListWeight(movieList: List<MainDataBean>) {
    LazyColumnFor(movieList, ) { bean ->
        val context = ContextAmbient.current
        Box(modifier = Modifier.wrapContentSize().clickable(onClick = {
            Toast.makeText(context, "${bean.title} ", Toast.LENGTH_SHORT).show()
        })) {
            Card(shape = RoundedCornerShape(8.dp),
                    elevation = 7.dp,
                    backgroundColor = Color(0xFFA882C1),
                    modifier = Modifier.fillMaxWidth().padding(12.dp).heightIn(150.dp)
            ) {
                buildListItemTextWeight(bean)
            }
        }
    }
}

@Composable
private fun buildListItemTextWeight(bean: MainDataBean) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        val titleReference = createRef()
        val urlReference = createRef()
        Text(text = "Title: ${bean.title}",
                color = Color(0xFFD50B53),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(5.dp).constrainAs(titleReference) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(urlReference.top)
                    end.linkTo(parent.end)
                }
        )
        Text(text = "Url: ${bean.url}",
                color = Color(0xFFB9C406),
                style = TextStyle.Default.copy(fontSize = 15.sp),
                modifier = Modifier.padding(2.dp).constrainAs(urlReference) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(titleReference.bottom)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun buildErrorWeight(throwable: Throwable) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val reference = createRef()
        Text("${throwable.localizedMessage} ",
                modifier = Modifier.padding(8.dp).constrainAs(reference) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
    }
}