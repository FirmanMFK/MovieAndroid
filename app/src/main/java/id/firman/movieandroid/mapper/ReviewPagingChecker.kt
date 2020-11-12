package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.ReviewListResponse

class ReviewPagingChecker : NetworkPagingChecker<ReviewListResponse> {
    override fun hasNextPage(response: ReviewListResponse): Boolean {
        return false
    }
}
