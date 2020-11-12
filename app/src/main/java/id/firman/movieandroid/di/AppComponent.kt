package id.firman.movieandroid.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import id.firman.movieandroid.MovieApp
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule ::class,
    MainActivityModule::class,
    AppModule::class])
interface AppComponent {

    fun inject(movieGuideApp: MovieApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
