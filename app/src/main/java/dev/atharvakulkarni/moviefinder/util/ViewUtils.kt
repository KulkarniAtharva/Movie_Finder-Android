package dev.atharvakulkarni.moviefinder.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import dev.atharvakulkarni.moviefinder.R

fun Context.dismissKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Context.loadImage(poster: String, imagePoster: ImageView) = Glide.with(this)
    .load(poster)
    .centerCrop()
    .thumbnail(0.5f)
    .placeholder(R.drawable.ic_launcher_background)
    .centerCrop()
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .into(imagePoster)

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok")
        {
            snackbar.dismiss()
        }
    }.show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}