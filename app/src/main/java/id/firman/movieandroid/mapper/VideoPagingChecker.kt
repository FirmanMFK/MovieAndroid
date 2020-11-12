package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.VideoListResponse

class VideoPagingChecker : NetworkPagingChecker<VideoListResponse> {
    override fun hasNextPage(response: VideoListResponse): Boolean {
        return false
    }
}
