package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.entity.PopularVideos
import com.example.domain.repo.NetworkRepository
import retrofit2.Response

class NetworkRepoImpl (private val apiService: ApiService) : NetworkRepository {
    override suspend fun getPopularVideos(videosNumber: String): Response<PopularVideos> {
        return apiService.getPopularVideos(videosNumber)
    }
}