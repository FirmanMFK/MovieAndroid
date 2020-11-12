package id.firman.movieandroid.view.ui.tv.tvlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.entity.Tv
import id.firman.movieandroid.repository.DiscoverRepository
import id.firman.movieandroid.utils.AbsentLiveData
import javax.inject.Inject

class TvListViewModel @Inject
constructor(private val discoverRepository: DiscoverRepository) : ViewModel() {

    private var pageNumber = 1

    private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
    val tvListLiveData: LiveData<Resource<List<Tv>>> = Transformations
        .switchMap(tvPageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadTvs(it)
            }
        }

    fun setTvPage(page: Int) {
        tvPageLiveData.value = page
    }

    init {
        tvPageLiveData.value = 1
    }

    fun loadMore() {
        pageNumber++
        tvPageLiveData.value = pageNumber
    }

    fun refresh() {
        tvPageLiveData.value?.let {
            tvPageLiveData.value = it
        }
    }
}