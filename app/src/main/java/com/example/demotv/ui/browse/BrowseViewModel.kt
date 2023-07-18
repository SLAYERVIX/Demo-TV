package com.example.demotv.ui.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.PopularVideos
import com.example.domain.repo.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject
constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _popularVideos : MutableStateFlow<PopularVideos?> = MutableStateFlow(null)
    val popularVideos : StateFlow<PopularVideos?> get() = _popularVideos

    fun getPopularVideos() = viewModelScope.launch(Dispatchers.IO) {
        val response = networkRepository.getPopularVideos("10")
        if (response.isSuccessful) {
            _popularVideos.value = response.body()
        }
    }
}