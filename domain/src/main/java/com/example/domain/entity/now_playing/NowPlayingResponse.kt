package com.example.domain.entity.now_playing

import com.example.domain.entity.movie.Result

data class NowPlayingResponse(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)