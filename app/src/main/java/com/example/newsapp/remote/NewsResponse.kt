package com.example.newsapp.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    @SerializedName("source")
    var modelSource: ModelSource? = null,

    @SerializedName("author")
    var author: String? = "",

    @SerializedName("title")
    var title: String? = "",

    @SerializedName("description")
    var description: String? = "",

    @SerializedName("url")
    var url: String? = "",

    @SerializedName("urlToImage")
    var urlToImage: String? = "",

    @SerializedName("publishedAt")
    var publishedAt: String? = ""
) : Parcelable

@Parcelize
data class ModelSource(
    @SerializedName("id")
    val id: String? = "",

    @SerializedName("name")
    val name: String? = ""
) : Parcelable
