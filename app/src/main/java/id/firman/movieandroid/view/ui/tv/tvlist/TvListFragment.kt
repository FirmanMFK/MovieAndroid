package id.firman.movieandroid.view.ui.tv.tvlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.firman.movieandroid.R
import id.firman.movieandroid.bind.FragmentDataBindingComponent
import id.firman.movieandroid.databinding.FragmentTvsBinding
import id.firman.movieandroid.di.Injectable
import id.firman.movieandroid.models.Status
import id.firman.movieandroid.utils.autoCleared
import id.firman.movieandroid.view.ui.adapter.TvListAdapter
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.common.RetryCallback
import kotlinx.android.synthetic.main.fragment_tvs.*
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject

class TvListFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val viewModel by viewModels<TvListViewModel> {
        viewModelFactory
    }
    private var binding by autoCleared<FragmentTvsBinding>()

    private var adapter by autoCleared<TvListAdapter>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tvs,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            lifecycleOwner = this@TvListFragment
            searchResult = viewModel.tvListLiveData
            callback = object : RetryCallback {
                override fun retry() {
                    viewModel.refresh()
                }
            }
        }

        initializeUI()
        subscribers()

    }

    private fun initializeUI() {
        intiToolbar(getString(R.string.fragment_tvs))
        adapter = TvListAdapter(dataBindingComponent, appExecutors) {
            findNavController().navigate(
                TvListFragmentDirections.actionTvsToTvDetail(
                    it
                )
            )
        }

        recyclerView_list_tvs.adapter = adapter
        recyclerView_list_tvs.layoutManager = GridLayoutManager(context, 2)
        recyclerView_list_tvs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1
                    && viewModel.tvListLiveData.value?.status != Status.LOADING
                ) {
                    viewModel.tvListLiveData.value?.let {
                        if (it.hasNextPage) viewModel.loadMore()
                    }
                }
            }
        })

        search_icon.setOnClickListener {
            findNavController().navigate(
                TvListFragmentDirections.actionTvsFragmentToTvSearchFragment()
            )
        }
    }

    private fun subscribers() {
        viewModel.tvListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.data != null && it.data.isNotEmpty()) {
                adapter.submitList(it.data)
            }
        })
    }

    private fun intiToolbar(title: String) {
        toolbar_title.text = title
    }

}
