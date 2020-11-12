package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.PeopleResponse

class PeoplePagingChecker : NetworkPagingChecker<PeopleResponse> {
    override fun hasNextPage(response: PeopleResponse): Boolean {
        return response.page < response.total_pages
    }
}
