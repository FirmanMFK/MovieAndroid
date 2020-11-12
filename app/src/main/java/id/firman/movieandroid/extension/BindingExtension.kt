package id.firman.movieandroid.extension

import android.view.View
import id.firman.movieandroid.models.Resource
import id.firman.movieandroid.models.Status
import org.jetbrains.anko.toast

fun View.bindResource(resource: Resource<Any>?, onSuccess: () -> Unit) {
    if (resource != null) {
        when (resource.status) {
            Status.LOADING -> Unit
            Status.SUCCESS -> onSuccess()
            Status.ERROR -> this.context.toast(resource.message.toString())
        }
    }
}
