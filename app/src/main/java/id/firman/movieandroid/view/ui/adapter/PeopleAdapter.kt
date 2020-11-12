package id.firman.movieandroid.view.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import id.firman.movieandroid.R
import id.firman.movieandroid.databinding.ItemPersonBinding
import id.firman.movieandroid.models.entity.Person
import id.firman.movieandroid.view.ui.common.AppExecutors
import id.firman.movieandroid.view.ui.common.DataBoundListAdapter

class PeopleAdapter(
    appExecutors: AppExecutors,
    private val dataBindingComponent: DataBindingComponent,
    private val movieOnClickCallback: ((Person) -> Unit)?
) : DataBoundListAdapter<Person, ItemPersonBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == (newItem)
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItemPersonBinding {
        val binding = DataBindingUtil.inflate<ItemPersonBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_person,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.person?.let {
                movieOnClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: ItemPersonBinding, item: Person) {
        binding.person = item
    }
}