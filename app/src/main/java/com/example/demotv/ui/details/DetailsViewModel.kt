package com.example.demotv.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.entity.video.VideoResponse
import com.example.domain.repo.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
): ViewModel() {
    suspend fun getVideo(id: Int): VideoResponse? = withContext(Dispatchers.IO) {
        val response = networkRepository.getVideos(id)
        if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("rabbit", "getVideo: ${response.code()}")
            null
        }
    }
}