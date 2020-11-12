package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.DiscoverTvResponse

class TvPagingChecker : NetworkPagingChecker<DiscoverTvResponse> {
    override fun hasNextPage(response: DiscoverTvResponse): Boolean {
        return response.page < response.total_pages
    }
}
