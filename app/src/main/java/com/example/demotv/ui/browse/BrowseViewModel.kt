package com.example.demotv.ui.browse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.genre.GenreResponse
import com.example.domain.entity.movie.MovieResponse
import com.example.domain.repo.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject
constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _genres : MutableStateFlow<GenreResponse?> = MutableStateFlow(null)
    val genres : StateFlow<GenreResponse?> get() = _genres


    private val _movies : MutableStateFlow<MovieResponse?> = MutableStateFlow(null)
    val movie : StateFlow<MovieResponse?> get() = _movies

    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        val response = networkRepository.getGenres()
        if (response.isSuccessful) {
            _genres.value = response.body()
        }
        else {
            Log.d("rabbit", "getGenres: ${response.code()}")
        }
    }

    suspend fun getMovies(id: Int): MovieResponse? = withContext(Dispatchers.IO) {
        val response = networkRepository.getMoviesByGenre(id)
        if (response.isSuccessful) {
            response.body()
        } else {
            Log.d("rabbit", "getMovies: ${response.code()}")
            null
        }
    }
}