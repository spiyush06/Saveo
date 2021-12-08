package com.saveo.movie.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saveo.movie.interfaces.NetworkResponseCallback
import com.saveo.movie.models.MovieList
import com.saveo.movie.repo.PictureRepository
import com.saveo.movie.utils.NetworkHelper

class MovieListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<MovieList>> =
        MutableLiveData<List<MovieList>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = PictureRepository.getInstance()

    fun fetchMovieFromServer(forceFetch: Boolean): MutableLiveData<List<MovieList>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getMovieList(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }
}