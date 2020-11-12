package id.firman.movieandroid.bind

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import id.firman.movieandroid.api.ApiService
import javax.inject.Inject

class FragmentBindingAdapter @Inject constructor(val fragment: Fragment) {
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        val movieUrl = url?.let { ApiService.getPosterPath(it) }
        movieUrl?.let {
            Glide.with(fragment)
                .load(it)
                .listener(listener)
                .into(imageView)
        }
    }
}
