package com.noob.apps.mvvmcountries.models

import android.app.Activity
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.gnecmedia.appentus.R

object BindingAdapters {
    @BindingAdapter("app:imageThumb")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String) {

        Log.d("ImageUrl", imageUrl)
        Glide.with(imageView.context) //1
                .load(imageUrl)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_error)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .into(imageView)
    }
}