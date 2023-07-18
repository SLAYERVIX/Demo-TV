package com.example.data.remote

import com.example.domain.entity.PopularVideos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("videos/popular")
   suspend fun getPopularVideos(
        @Query("per_page") videosNumber : String,
        @Header("Authorization") apiKey : String = Constants.API_KEY
    ) : Response<PopularVideos>
}