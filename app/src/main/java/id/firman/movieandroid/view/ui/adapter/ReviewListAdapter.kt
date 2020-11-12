package id.firman.movieandroid.view.ui.adapter

import android.view.View
import id.firman.movieandroid.R
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.Review
import id.firman.movieandroid.view.viewholder.BaseAdapter
import id.firman.movieandroid.view.viewholder.BaseViewHolder
import id.firman.movieandroid.view.viewholder.ReviewListViewHolder
import id.firman.movieandroid.view.viewholder.SectionRow

class ReviewListAdapter : BaseAdapter() {

    init {
        addSection(ArrayList<Review>())
    }

    fun addReviewList(resource: Resource<List<Review>>) {
        resource.data?.let {
            sections()[0].addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_review
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return ReviewListViewHolder(view)
    }
}
