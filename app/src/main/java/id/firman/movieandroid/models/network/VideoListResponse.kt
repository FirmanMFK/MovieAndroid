package id.firman.movieandroid.models.network

import id.firman.movieandroid.models.Video

data class VideoListResponse(
    val id: Int,
    val results: List<Video>
) : NetworkResponseModel
