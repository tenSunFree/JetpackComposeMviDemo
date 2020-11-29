package com.home.jetpackcomposemvidemo.main.model

class MainDataResponse : ArrayList<MainDataResponse.MainDataResponseItem>() {

    data class MainDataResponseItem(
            val albumId: Int,
            val id: Int,
            val title: String,
            val url: String,
            val thumbnailUrl: String
    )
}