package com.noob.apps.mvvmcountries.models

import android.app.Activity
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmadrosid.svgloader.SvgLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.gnecmedia.appentus.R

object BindingAdapters {
    @BindingAdapter("app:imageThumb")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String) {

        Log.d("ImageUrl", imageUrl)

       /* SvgLoader.pluck()
            .with(imageView.context as Activity?)
            .setPlaceHolder(
                com.gnecmedia.appentus.R.mipmap.ic_launcher,
                com.gnecmedia.appentus.R.mipmap.ic_launcher
            )
            .load("https://i.picsum.photos/id/0/5616/3744.jpg?hmac=3GAAioiQziMGEtLbfrdbcoenXoWAW-zlyEAMkfEdBzQ", imageView)
        */

        Glide.with(imageView.context) //1
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .into(imageView)
    }
}