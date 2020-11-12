package id.firman.movieandroid.view.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.repository.MovieRepository
import id.firman.movieandroid.utils.AbsentLiveData
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val movieIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val keywordListLiveData = Transformations.switchMap(movieIdLiveData) {
        movieIdLiveData.value?.let {
            repository.loadKeywordList(it)
        } ?: AbsentLiveData.create()
    }

    val videoListLiveData = Transformations.switchMap(movieIdLiveData) {
        movieIdLiveData.value?.let {
            repository.loadVideoList(it)
        } ?: AbsentLiveData.create()
    }

    val reviewListLiveData = Transformations.switchMap(movieIdLiveData) {
        movieIdLiveData.value?.let {
            repository.loadReviewsList(it)
        } ?: AbsentLiveData.create()
    }


    fun setMovieId(id: Int?) {
        movieIdLiveData.value = id
    }

}
