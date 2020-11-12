package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.NetworkResponseModel

interface NetworkPagingChecker<in FROM : NetworkResponseModel> {
    fun hasNextPage(response: FROM): Boolean
}
