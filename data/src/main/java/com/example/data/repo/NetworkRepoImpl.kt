package com.example.data.repo

import com.example.data.remote.MoviesApiService
import com.example.domain.entity.genre.GenreResponse
import com.example.domain.entity.movie.MovieResponse
import com.example.domain.entity.video.VideoResponse
import com.example.domain.repo.NetworkRepository
import retrofit2.Response

class NetworkRepoImpl (private val apiService: MoviesApiService) : NetworkRepository {
    override suspend fun getGenres(): Response<GenreResponse> {
        return apiService.getGenres()
    }

    override suspend fun getMoviesByGenre(id : Int) : Response<MovieResponse> {
        return apiService.getMoviesByGenre(genreId = id)
    }

    override suspend fun getVideos(id: Int): Response<VideoResponse> {
        return apiService.getVideos(movieId = id)
    }
}