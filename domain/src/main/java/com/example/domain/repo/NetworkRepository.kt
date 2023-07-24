package com.example.domain.repo

import com.example.domain.entity.genre.GenreResponse
import com.example.domain.entity.movie.MovieResponse
import com.example.domain.entity.now_playing.NowPlayingResponse
import com.example.domain.entity.video.VideoResponse
import retrofit2.Response

interface NetworkRepository {
    suspend fun getGenres() : Response<GenreResponse>
    suspend fun getMoviesByGenre(id : Int) : Response<MovieResponse>
    suspend fun getVideos(id : Int) : Response<VideoResponse>

    suspend fun getNowPlaying() : Response<NowPlayingResponse>
}