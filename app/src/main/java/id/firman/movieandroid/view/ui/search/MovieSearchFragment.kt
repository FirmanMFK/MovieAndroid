package id.firman.movieandroid.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import id.firman.movieandroid.R
import id.firman.movieandroid.bind.FragmentDataBindingComponent
import id.firman.movieandroid.databinding.FragmentSearchBinding
import id.firman.movieandroid.utils.autoCleared
import id.firman.movieandroid.view.ui.adapter.MovieSearchListAdapter
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.search.base.SearchFragmentBase
import kotlinx.android.synthetic.main.toolbar_search_iconfied.*
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import id.firman.movieandroid.di.Injectable
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class MovieSearchFragment : SearchFragmentBase(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<MovieSearchViewModel> { viewModelFactory }

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var binding by autoCleared<FragmentSearchBinding>()

    private var movieAdapter by autoCleared<MovieSearchListAdapter>()

//    private lateinit var mSearchViewAdapter: SuggestionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        return binding.root
    }

    override fun setSearchViewHint() {
        search_view.queryHint = "Search Movies"
    }

    override fun setFilterTabName(tab: TabLayout.Tab?) {
        tab?.text = getString(R.string.filter_movies_tab_name)
    }

    override fun setBindingVariables() {/*DO nothing*/}

    override fun navigateFromSearchFragmentToSearchFragmentResultFilter(bundle: Bundle) {
        findNavController().navigate(
            R.id.action_movieSearchFragment_to_movieSearchFragmentResultFilter,
            bundle
        )
    }

    override fun navigateFromSearchFragmentToSearchFragmentResult(query: String) {
        findNavController().navigate(
            MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieSearchFragmentResult(
                query
            )
        )
    }

    override fun navigateFromSearchFragmentToListItemsFragment() {
        findNavController().navigate(
            MovieSearchFragmentDirections.actionMovieSearchFragmentToMoviesFragment()
        )
    }

    override fun observeSuggestions(newText: String?) {
        viewModel.movieSuggestions.observe(
            viewLifecycleOwner,
            Observer {
                if (!it.isNullOrEmpty() && tabs.getTabAt(0)?.isSelected!!) {
                    showSuggestionViewAndHideRecentSearches()
                }
                movieAdapter.submitList(it)

                if (newText != null) {
                    if ((newText.isEmpty() || newText.isBlank()) && tabs.getTabAt(0)?.isSelected!!) {
                        hideSuggestionViewAndShowRecentSearches()
                        movieAdapter.submitList(null)
                    }
                }
            })
    }

    override fun setRecyclerViewAdapter() {
        movieAdapter = MovieSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetail(
                    it
                )
            )
        }

        binding.root.recyclerView_suggestion.adapter = movieAdapter

        recyclerView_suggestion.layoutManager = LinearLayoutManager(context)
    }


    override fun observeAndSetRecentQueries() {
        viewModel.getMovieRecentQueries().observe(viewLifecycleOwner, Observer { it ->
            if (!it.isNullOrEmpty()) {
                val queries = it.mapNotNull { it.query }.filter { it.isNotEmpty() }
                if (queries.isNotEmpty()) setListViewOfRecentQueries(queries)
            }
        })
    }

    override fun deleteAllRecentQueries() {
        viewModel.deleteAllMovieRecentQueries()
    }

    override fun setSuggestionsQuery(newText: String?) {
        viewModel.setMovieSuggestionsQuery(newText)
    }

}
