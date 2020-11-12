package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.entity.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel
