package id.firman.movieandroid.view.ui.search.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.entity.Tv
import id.firman.movieandroid.repository.DiscoverRepository
import id.firman.movieandroid.utils.AbsentLiveData
import javax.inject.Inject

class TvSearchFilterViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {

    private var pageFiltersNumber = 1
    private var sort: String? = "popularity.desc"
    private var year: Int? = null
    private var keyword: String? = null
    private var genres: String? = null
    private var language: String? = null
    private var runtime: Int? = null
    private var rating: Int? = null
    private val searchTvFilterPageLiveData: MutableLiveData<Int> = MutableLiveData()

    val searchTvListFilterLiveData: LiveData<Resource<List<Tv>>> = Transformations
        .switchMap(searchTvFilterPageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadFilteredTvs(
                    rating,
                    sort,
                    year,
                    keyword,
                    genres,
                    language,
                    runtime,
                    it
                )
            }
        }

    fun setFilters(
        rating: Int? = null,
        sort: String? = null,
        year: Int? = null,
        keywords: String? = null,
        genres: String? = null,
        language: String? = null,
        runtime: Int? = null,
        page: Int
    ) {
        this.sort = sort
        this.year = year
        this.language = language
        this.keyword = keywords
        this.runtime = runtime
        this.genres = genres
        this.rating = rating
        searchTvFilterPageLiveData.value = page
    }

    //For Testing
    fun setPage(page: Int?) {
        searchTvFilterPageLiveData.value = page
    }

    fun loadMoreFilters() {
        pageFiltersNumber++
        searchTvFilterPageLiveData.value = pageFiltersNumber
    }

    fun resetFilterValues() {
        this.rating = null
        this.genres = null
        this.keyword = null
        this.language = null
        this.runtime = null
        this.year = null

        this.pageFiltersNumber = 1
    }

    val totalTvFilterResult = Transformations.switchMap(searchTvFilterPageLiveData) {
        it?.let {
            discoverRepository.getTotalTvFilteredResults()
        } ?: AbsentLiveData.create()
    }


    fun refresh() {
        searchTvFilterPageLiveData.value?.let {
            searchTvFilterPageLiveData.value = it
        }
    }

}