package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.entity.Person

data class PeopleResponse(
    val page: Int,
    val results: List<Person>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel
