package id.firman.movieandroid.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import id.firman.movieandroid.view.ui.main.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
