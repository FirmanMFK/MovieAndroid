package id.firman.movieandroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.firman.movieandroid.api.ApiResponse
import id.firman.movieandroid.api.service.PeopleService
import id.firman.movieandroid.mapper.MoviePersonPagingChecker
import id.firman.movieandroid.mapper.TvPersonPagingChecker
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.entity.MoviePerson
import id.firman.movieandroid.models.entity.MoviePersonResult
import id.firman.movieandroid.models.entity.TvPerson
import id.firman.movieandroid.models.entity.TvPersonResult
import id.firman.movieandroid.models.network.MoviePersonResponse
import id.firman.movieandroid.models.network.TvPersonResponse
import id.firman.movieandroid.room.PeopleDao
import id.firman.movieandroid.utils.AbsentLiveData
import id.firman.movieandroid.view.ui.common.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonWorksRepository @Inject constructor(
    private val peopleService: PeopleService,
    private val peopleDao: PeopleDao,
    private val appExecutors: AppExecutors
) {

    fun loadMoviesForPerson(personId: Int): LiveData<Resource<List<MoviePerson>>> {
        return object :
            NetworkBoundResource<List<MoviePerson>, MoviePersonResponse, MoviePersonPagingChecker>(
                appExecutors
            ) {
            override fun saveCallResult(items: MoviePersonResponse) {

                val movieIds: List<Int> = items.cast.map { it.id }

                peopleDao.insertMovieForPerson(movies = items.cast)
                val moviePersonResult = MoviePersonResult(
                    moviesIds = movieIds,
                    personId = personId
                )
                peopleDao.insertMoviePersonResult(moviePersonResult)
            }

            override fun shouldFetch(data: List<MoviePerson>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<MoviePerson>> {
                return Transformations.switchMap(peopleDao.getMoviePersonResultByPersonIdLiveData(personId)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        peopleDao.loadMoviesForPerson(searchData.moviesIds)
                    }
                }
            }

            override fun pageChecker(): MoviePersonPagingChecker {
                return MoviePersonPagingChecker()
            }

            override fun createCall(): LiveData<ApiResponse<MoviePersonResponse>> {
                return peopleService.fetchPersonMovies(id = personId)
            }
        }.asLiveData()
    }

    fun loadTvsForPerson(personId: Int): LiveData<Resource<List<TvPerson>>> {
        return object :
            NetworkBoundResource<List<TvPerson>, TvPersonResponse, TvPersonPagingChecker>(
                appExecutors
            ) {
            override fun saveCallResult(items: TvPersonResponse) {

                val movieIds: List<Int> = items.cast.map { it.id }

                peopleDao.insertTvForPerson(tvs = items.cast)
                val moviePersonResult = TvPersonResult(
                    tvsIds = movieIds,
                    personId = personId
                )
                peopleDao.insertTvPersonResult(moviePersonResult)
            }

            override fun shouldFetch(data: List<TvPerson>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<TvPerson>> {
                return Transformations.switchMap(peopleDao.getTvPersonResultByPersonIdLiveData(personId)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        peopleDao.loadTvsForPerson(searchData.tvsIds)
                    }
                }
            }

            override fun pageChecker(): TvPersonPagingChecker {
                return TvPersonPagingChecker()
            }

            override fun createCall(): LiveData<ApiResponse<TvPersonResponse>> {
                return peopleService.fetchPersonTvs(id = personId)
            }
        }.asLiveData()
    }


}