package com.gnecmedia.appentus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrosid.svgloader.SvgLoader
import com.gnecmedia.appentus.R
import com.gnecmedia.appentus.adapters.GridSpacingItemDecoration
import com.gnecmedia.appentus.adapters.PictureListAdapter
import com.gnecmedia.appentus.adapters.SpacesItemDecoration
import com.gnecmedia.appentus.databinding.ActivityMain2Binding
import com.gnecmedia.appentus.viewModels.PictureListViewModel

class MainActivity2 : AppCompatActivity() {

    private lateinit var mAdapter: PictureListAdapter
    private lateinit var mViewModel: PictureListViewModel
    private lateinit var mActivityBinding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main2)

        mViewModel = ViewModelProviders.of(this).get(PictureListViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = PictureListAdapter()
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            val spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            addItemDecoration( GridSpacingItemDecoration(2, spacingInPixels,true));
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        mViewModel.fetchCountriesFromServer(false).observe(this, Observer { kt ->
            mAdapter.setData(kt)
        })
        mViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
                mActivityBinding.floatingActionButton.hide()
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
                mActivityBinding.floatingActionButton.show()
            }
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SvgLoader.pluck().close()
    }
}