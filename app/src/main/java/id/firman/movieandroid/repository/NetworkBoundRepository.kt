package id.firman.movieandroid.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.firman.movieandroid.api.ApiEmptyResponse
import id.firman.movieandroid.api.ApiErrorResponse
import id.firman.movieandroid.api.ApiResponse
import id.firman.movieandroid.api.ApiSuccessResponse
import id.firman.movieandroid.mapper.NetworkPagingChecker
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.network.NetworkResponseModel
import id.firman.movieandroid.view.ui.common.AppExecutors

abstract class NetworkBoundResource<
        CacheType,
        NetworkType : NetworkResponseModel,
        PageChecker: NetworkPagingChecker<NetworkType>>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<CacheType>>()

    init {
        result.value = Resource.loading(null)

        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data: CacheType ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData, true))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<CacheType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) {
            setValue(Resource.loading(null))
        }
        result.addSource(apiResponse) { response : ApiResponse<NetworkType> ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData, pageChecker().hasNextPage(response.body)))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData, false))
                        }
                    }
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }

        }
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<NetworkType>): NetworkType {
        return response.body
    }

    @MainThread
    private fun setValue(newValue: Resource<CacheType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData() = result as LiveData<Resource<CacheType>>

    @WorkerThread
    protected abstract fun saveCallResult(items: NetworkType)

    @MainThread
    protected abstract fun shouldFetch(data: CacheType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<NetworkType>>

    @MainThread
    protected abstract fun pageChecker(): PageChecker

    protected open fun onFetchFailed() {}
}
