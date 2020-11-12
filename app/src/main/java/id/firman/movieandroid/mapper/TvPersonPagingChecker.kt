package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.TvPersonResponse

class TvPersonPagingChecker : NetworkPagingChecker<TvPersonResponse> {
    override fun hasNextPage(response: TvPersonResponse): Boolean {
        return false
    }
}
