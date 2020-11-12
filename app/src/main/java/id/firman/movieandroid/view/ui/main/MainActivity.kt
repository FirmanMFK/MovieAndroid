package id.firman.movieandroid.view.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rbddevs.splashy.Splashy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import id.firman.movieandroid.R
import id.firman.movieandroid.extension.*
import id.firman.movieandroid.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSplashy()
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
        setOnNavigationItemReselected()

        currentNavController?.observe(this, Observer { navController ->
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (isMainFragment(destination)) {
                    findViewById<BottomNavigationView>(R.id.bottom_navigation).visible()
                } else findViewById<BottomNavigationView>(R.id.bottom_navigation).gone()
            }
        })
    }


    override fun onBackPressed() {

        val currentFragment: Fragment? = supportFragmentManager.getCurrentNavigationFragment()
        val currentFragmentName = (currentFragment as Fragment).javaClass.simpleName

        if (currentFragmentName == MOVIE_LIST_FRAGMENT) {
            if (!currentFragment.isRecyclerViewScrollPositionZero(R.id.recyclerView_list_movies)!!) {
                currentFragment.setSmoothScrollToZero(R.id.recyclerView_list_movies)
            } else {
                super.onBackPressed()
            }
        } else if (currentFragmentName == TV_LIST_FRAGMENT) {
            if (!currentFragment.isRecyclerViewScrollPositionZero(R.id.recyclerView_list_tvs)!!) {
                currentFragment.setSmoothScrollToZero(R.id.recyclerView_list_tvs)
            } else {
                super.onBackPressed()
            }
        } else super.onBackPressed()
    }

    companion object {
        const val MOVIE_LIST_FRAGMENT = "MovieListFragment"
        const val TV_LIST_FRAGMENT = "TvListFragment"

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navGraphIds = listOf(R.navigation.movie, R.navigation.tv)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        //Whenever the selected controller changes, setup the action bar.
//    controller.observe(this, Observer { navController ->
//      setupActionBarWithNavController(navController)
//    })
        currentNavController = controller
    }

//  override fun onSupportNavigateUp(): Boolean {
//    return currentNavController?.value?.navigateUp() ?: false
//  }

    private fun setOnNavigationItemReselected() {
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.movie -> supportFragmentManager.getCurrentNavigationFragment()
                    ?.setSmoothScrollToZero(R.id.recyclerView_list_movies)
                R.id.tv -> supportFragmentManager.getCurrentNavigationFragment()
                    ?.setSmoothScrollToZero(R.id.recyclerView_list_tvs)

            }
        }
    }


    private fun isMainFragment(destination: NavDestination): Boolean =
        destination.id == R.id.moviesFragment || destination.id == R.id.tvsFragment


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    private fun setSplashy() {
        Splashy(this)
            .setLogo(R.mipmap.ic_launcher_foreground)
            .setTitle("")
            .setTitleColor(R.color.colorPrimary)
            .showProgress(true)
            .setProgressColor(R.color.colorPrimary)
            .setProgressColor(R.color.colorPrimary)
            .setBackgroundResource(R.color.backgroundDarker)
            .setFullScreen(true)
            .setTime(3000)
            .show()
    }
}
