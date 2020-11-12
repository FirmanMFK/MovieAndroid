package id.firman.movieandroid.view.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.entity.Movie
import id.firman.movieandroid.repository.DiscoverRepository
import id.firman.movieandroid.utils.AbsentLiveData
import id.firman.movieandroid.view.ui.common.AppExecutors
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val discoverRepository: DiscoverRepository
) : ViewModel() {

    private var pageNumber = 1
    private val moviePageLiveData: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var appExecutors: AppExecutors

    val movieListLiveData: LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(moviePageLiveData) {
            if (it == null) {
                AbsentLiveData.create()
            } else {
                discoverRepository.loadMovies(it)
            }
        }

    init {
        moviePageLiveData.value = 1
    }

    fun setMoviePage(page: Int) {
        moviePageLiveData.value = page
    }

    fun loadMore() {
        pageNumber++
        moviePageLiveData.value = pageNumber
    }

    fun refresh() {
        moviePageLiveData.value?.let {
            moviePageLiveData.value = it
        }
    }

}
