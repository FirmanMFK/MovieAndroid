package id.firman.movieandroid.view.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import id.firman.movieandroid.R
import id.firman.movieandroid.databinding.ItemTvBinding
import id.firman.movieandroid.models.entity.Tv
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.common.DataBoundListAdapter


class TvListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val tvOnClickCallback: ((Tv) -> Unit)?
) : DataBoundListAdapter<Tv, ItemTvBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Tv>() {
        override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemTvBinding {
        val binding = DataBindingUtil.inflate<ItemTvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_tv,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.tv?.let {
                tvOnClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ItemTvBinding, item: Tv) {
        binding.tv = item
    }

}

