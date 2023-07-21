package com.example.data.remote

import com.example.data.Constants
import com.example.domain.entity.genre.GenreResponse
import com.example.domain.entity.movie.MovieResponse
import com.example.domain.entity.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Header("Authorization") apiKey: String = Constants.TOKEN,
    ): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Header("Authorization") apiKey: String = Constants.TOKEN,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("with_genres") genreId: Int
    ): Response<MovieResponse>


    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") apiKey: String = Constants.TOKEN,
    ): Response<VideoResponse>
}