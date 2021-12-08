package com.saveo.movie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.saveo.movie.R
import com.saveo.movie.databinding.ActivityDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var mActivityBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        toolbar?.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar?.setNavigationOnClickListener { onBackPressed() }

        val description=intent.getStringExtra("description")
        val title=intent.getStringExtra("title")
        val release_date=intent.getStringExtra("release_date")
        val ratingValue=intent.getStringExtra("rating")
        val image=intent.getStringExtra("image_url")
        val genres_1=intent.getStringExtra("genres_1")
        val genres_2=intent.getStringExtra("genres_2")
        val genres_3=intent.getStringExtra("genres_3")
        val cities=intent.getStringExtra("cities")
        val users=intent.getStringExtra("users")
        val hour=intent.getStringExtra("hour")
        mActivityBinding.txvMovieName.setText(title)
        mActivityBinding.txvDescription.setText(description)
        mActivityBinding.txvTitle.setText(title)
        mActivityBinding.txvDate.setText("R  | " +hour + "  |  " +release_date?.let { getDateTime(it) })
        mActivityBinding.ratingBar.setRating(ratingValue!!.toFloat())
        mActivityBinding.txvRating.setText(ratingValue)
        mActivityBinding.txvFirstGenres.setText(genres_1)
        mActivityBinding.txvSecondGenres.setText(genres_2)
        mActivityBinding.txvThirdGenres.setText(genres_3)
        mActivityBinding.txvReview.setText("Reviews : "+cities+" (Cities) | " +users+" (User)")

        Glide.with(applicationContext) //1
            .load(image)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_error)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .into(mActivityBinding.imgMovie)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM-dd-yyyy")
            val netDate = Date(s!!.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}