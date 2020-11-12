package id.firman.movieandroid.view.ui.search.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.firman.movieandroid.R
import id.firman.movieandroid.bind.FragmentDataBindingComponent
import id.firman.movieandroid.databinding.FragmentSearchResultFilterBinding
import id.firman.movieandroid.di.Injectable
import id.firman.movieandroid.models.Status
import id.firman.movieandroid.utils.autoCleared
import id.firman.movieandroid.view.ui.adapter.TvSearchListAdapter
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.common.RetryCallback
import kotlinx.android.synthetic.main.fragment_search_result_filter.*
import kotlinx.android.synthetic.main.fragment_search_result_filter.view.*
import javax.inject.Inject

class TvSearchResultFilterFragment : SearchResultFilterFragmentBase(), Injectable,
    androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private val viewModel by viewModels<TvSearchFilterViewModel> { viewModelFactory }
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private var binding by autoCleared<FragmentSearchResultFilterBinding>()
    private var adapter by autoCleared<TvSearchListAdapter>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_result_filter,
            container,
            false
        )
        return binding.root
    }

    override fun getFilterMap(): HashMap<String, ArrayList<String>>? {
        @Suppress("UNCHECKED_CAST")
        return arguments?.getSerializable("key") as HashMap<String, ArrayList<String>>?
    }

    override fun setBindingVariables() {
        with(binding) {
            lifecycleOwner = this@TvSearchResultFilterFragment
            totalFilterResult = viewModel.totalTvFilterResult
            selectedFilters = setSelectedFilters()
            callback = object : RetryCallback {
                override fun retry() {
                    viewModel.refresh()
                }
            }
        }
    }

    override fun observeSubscribers() {
        viewModel.searchTvListFilterLiveData.observe(viewLifecycleOwner, Observer {
            binding.resource = viewModel.searchTvListFilterLiveData.value
            if (it.data != null && it.data.isNotEmpty()) {
                adapter.submitList(it.data)
            }
        })
    }

    override fun setRecyclerViewAdapter() {
        adapter = TvSearchListAdapter(
            appExecutors,
            dataBindingComponent
        ) {
            findNavController().navigate(
                TvSearchResultFilterFragmentDirections.actionTvSearchFragmentResultFilterToTvDetail(
                    it
                )
            )
        }

        binding.root.filtered_items_recycler_view.adapter = adapter

        filtered_items_recycler_view.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun loadMoreFilters() {
        viewModel.loadMoreFilters()
    }

    override fun isLoading(): Boolean {
        return viewModel.searchTvListFilterLiveData.value?.status == Status.LOADING
    }

    override fun navigateFromSearchResultFilterFragmentToSearchFragment() {
        findNavController().navigate(
            TvSearchResultFilterFragmentDirections.actionTvSearchFragmentResultFilterToTvSearchFragment()
        )
    }


    override fun hasNextPage(): Boolean {
        viewModel.searchTvListFilterLiveData.value?.let {
            return it.hasNextPage
        }
        return false
    }


    override fun resetAndLoadFiltersSortedBy(order: String) {
        viewModel.resetFilterValues()
        viewModel.setFilters(
            filtersData?.rating,
            order,
            filtersData?.year,
            filtersData?.genres,
            filtersData?.keywords,
            filtersData?.language,
            filtersData?.runtime,
            1
        )
    }
}