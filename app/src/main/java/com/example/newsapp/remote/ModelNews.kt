package com.example.newsapp.remote

import com.google.gson.annotations.SerializedName

data class ModelNews(
    @SerializedName("status")
    val status: String = "",

    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("articles")
    val modelArticle: List<NewsResponse> = emptyList()
)
