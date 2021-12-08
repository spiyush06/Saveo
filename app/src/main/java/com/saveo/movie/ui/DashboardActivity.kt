package com.saveo.movie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.saveo.movie.CenterZoomLayoutManager
import com.saveo.movie.R
import com.saveo.movie.R.drawable.*
import com.saveo.movie.adapters.GridSpacingItemDecoration
import com.saveo.movie.adapters.MovieListAdapter
import com.saveo.movie.databinding.ActivityDashboardBinding
import com.saveo.movie.viewModels.MovieListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.dashboard_movie_item.view.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mAdapter: MovieListAdapter
    private lateinit var mMovieViewModel: MovieListViewModel
    private lateinit var mActivityBinding: ActivityDashboardBinding

    val colorList: MutableList<String> = ArrayList()
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        toolbar?.setNavigationIcon(ic_menu)




        //setSupportActionBar(mActivityBinding.toolbar);
        mMovieViewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)

        mActivityBinding.movieViewModel = mMovieViewModel
        mActivityBinding.lifecycleOwner = this


        dummyStrings()
        setUpRecyclerView()
        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = MovieListAdapter()
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            val spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            addItemDecoration( GridSpacingItemDecoration(3, spacingInPixels,true));
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        /*mViewModel.fetchCountriesFromServer(false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })*/
        mMovieViewModel.fetchMovieFromServer(false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mMovieViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        mMovieViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
            }
        })
        mMovieViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun dummyStrings(): List<String>? {
        colorList.add("https://image.tmdb.org/t/p/w500/7SPhr7Qj39vbnfF9O2qHRYaKHAL.jpg")
        colorList.add("https://image.tmdb.org/t/p/w500/7SPhr7Qj39vbnfF9O2qHRYaKHAL.jpg")
        colorList.add("https://image.tmdb.org/t/p/w500/7SPhr7Qj39vbnfF9O2qHRYaKHAL.jpg")
        colorList.add("https://image.tmdb.org/t/p/w500/7SPhr7Qj39vbnfF9O2qHRYaKHAL.jpg")
        return colorList
    }

    private fun setUpRecyclerView() {

        for (i in 0..colorList.size-1) {
            adapter.add(InnerClass(colorList.get(i)))
        }
        mActivityBinding.rvOffer.layoutManager = CenterZoomLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        mActivityBinding.rvOffer.adapter = adapter
        mActivityBinding.rvOffer.adapter!!.notifyDataSetChanged()
        mActivityBinding.rvOffer.scheduleLayoutAnimation()

    }

    inner class InnerClass(var imgList: String) : Item<ViewHolder>() {
        override fun getLayout(): Int {
            return R.layout.dashboard_movie_item
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
           // viewHolder.itemView.ivProductImg.setBackgroundColor(Color.parseColor(imgList))

            Glide.with(applicationContext) //1
                .load(imgList)
                .placeholder(progress_animation)
                .error(ic_error)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .into(viewHolder.itemView.ivProductImg)
        }
    }
}