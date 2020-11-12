package id.firman.movieandroid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.firman.movieandroid.view.ui.movie.MovieDetailFragment
import id.firman.movieandroid.view.ui.movie.MovieListFragment
import id.firman.movieandroid.view.ui.search.MovieSearchFragment
import id.firman.movieandroid.view.ui.search.TvSearchFragment
import id.firman.movieandroid.view.ui.search.filter.MovieSearchResultFilterFragment
import id.firman.movieandroid.view.ui.search.filter.TvSearchResultFilterFragment
import id.firman.movieandroid.view.ui.search.result.MovieSearchResultFragment
import id.firman.movieandroid.view.ui.search.result.TvSearchResultFragment
import id.firman.movieandroid.view.ui.tv.tvdetail.TvDetailFragment
import id.firman.movieandroid.view.ui.tv.tvlist.TvListFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeTvDetailFragment(): TvDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeTvListFragment(): TvListFragment


    @ContributesAndroidInjector
    abstract fun contributeMoviesSearchFragment(): MovieSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeTvSearchFragment(): TvSearchFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchResultFragment(): MovieSearchResultFragment

    @ContributesAndroidInjector
    abstract fun contributeTvSearchResultFragment(): TvSearchResultFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieSearchResultFilterFragment(): MovieSearchResultFilterFragment

    @ContributesAndroidInjector
    abstract fun contributeTvSearchResultFilterFragment(): TvSearchResultFilterFragment


}