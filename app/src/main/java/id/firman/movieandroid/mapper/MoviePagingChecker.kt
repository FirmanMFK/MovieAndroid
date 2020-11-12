package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.DiscoverMovieResponse

class MoviePagingChecker : NetworkPagingChecker<DiscoverMovieResponse> {
    override fun hasNextPage(response: DiscoverMovieResponse): Boolean {
        return response.page < response.total_pages
    }
}
