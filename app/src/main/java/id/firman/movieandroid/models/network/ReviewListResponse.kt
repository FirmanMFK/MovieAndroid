package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.Review

class ReviewListResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
) : NetworkResponseModel
