package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.entity.TvPerson

class TvPersonResponse(
    val cast: List<TvPerson>,
    val id : Int
) : NetworkResponseModel