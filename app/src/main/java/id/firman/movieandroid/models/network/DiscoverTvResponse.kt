package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.entity.Tv

data class DiscoverTvResponse(
    val page: Int,
    val results: List<Tv>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel
