package id.firman.movieandroid.view.ui.adapter

import android.view.View
import id.firman.movieandroid.R
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.Video
import id.firman.movieandroid.view.viewholder.BaseAdapter
import id.firman.movieandroid.view.viewholder.BaseViewHolder
import id.firman.movieandroid.view.viewholder.SectionRow
import id.firman.movieandroid.view.viewholder.VideoListViewHolder

class VideoListAdapter(private val delegate: VideoListViewHolder.Delegate) : BaseAdapter() {

    init {
        addSection(ArrayList<Video>())
    }

    fun addVideoList(resource: Resource<List<Video>>) {
        resource.data?.let {
            sections()[0].addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow): Int {
        return R.layout.item_video
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return VideoListViewHolder(view, delegate)
    }
}
