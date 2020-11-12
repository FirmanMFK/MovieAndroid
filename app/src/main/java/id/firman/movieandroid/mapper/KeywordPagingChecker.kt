package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.KeywordListResponse

class KeywordPagingChecker : NetworkPagingChecker<KeywordListResponse> {
    override fun hasNextPage(response: KeywordListResponse): Boolean {
        return false
    }
}