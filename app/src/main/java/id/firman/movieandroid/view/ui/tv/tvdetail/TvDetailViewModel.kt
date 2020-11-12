package id.firman.movieandroid.view.ui.tv.tvdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.firman.movieandroid.repository.TvRepository
import id.firman.movieandroid.utils.AbsentLiveData
import javax.inject.Inject


class TvDetailViewModel @Inject
constructor(private val repository: TvRepository) : ViewModel() {

    private val tvIdLiveData: MutableLiveData<Int> = MutableLiveData()

    val keywordListLiveData = Transformations
        .switchMap(tvIdLiveData) {
            tvIdLiveData.value?.let {
                repository.loadKeywordList(it)
            } ?: AbsentLiveData.create()
        }

    val videoListLiveData = Transformations
        .switchMap(tvIdLiveData) {
            tvIdLiveData.value?.let {
                repository.loadVideoList(it)
            } ?: AbsentLiveData.create()
        }

    val reviewListLiveData = Transformations.switchMap(tvIdLiveData) {
        tvIdLiveData.value?.let {
            repository.loadReviewsList(it)
        } ?: AbsentLiveData.create()
    }

    fun setTvId(id: Int) = tvIdLiveData.postValue(id)
}
