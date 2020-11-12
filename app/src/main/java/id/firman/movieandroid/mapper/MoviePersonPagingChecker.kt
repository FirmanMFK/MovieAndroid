package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.MoviePersonResponse

class MoviePersonPagingChecker : NetworkPagingChecker<MoviePersonResponse> {
    override fun hasNextPage(response: MoviePersonResponse): Boolean {
        return false
    }
}
