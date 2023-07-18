package com.example.domain.repo

import com.example.domain.entity.PopularVideos
import retrofit2.Response

interface NetworkRepository {
    suspend fun getPopularVideos(videosNumber : String) : Response<PopularVideos>
}