package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.entity.MoviePerson

class MoviePersonResponse(
    val cast: List<MoviePerson>,
    val id : Int
) : NetworkResponseModel