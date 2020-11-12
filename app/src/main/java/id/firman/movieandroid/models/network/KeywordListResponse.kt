package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.Keyword

data class KeywordListResponse(
    val id: Int,
    val keywords: List<Keyword>
) : NetworkResponseModel
