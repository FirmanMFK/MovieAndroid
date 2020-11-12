package id.firman.movieandroid.mapper

import id.firman.movieandroid.models.network.PersonDetail

class PersonDetailPagingChecker : NetworkPagingChecker<PersonDetail> {
    override fun hasNextPage(response: PersonDetail): Boolean {
        return false
    }
}
