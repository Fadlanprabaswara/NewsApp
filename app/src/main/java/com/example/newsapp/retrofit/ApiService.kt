package com.example.newsapp.retrofit

import com.example.newsapp.remote.ModelNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getHeadlines(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getSports(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getTechnology(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getBusiness(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getEntertainment(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>

    @GET("top-headlines")
    fun getHealth(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String?
    ): Call<ModelNews>
}