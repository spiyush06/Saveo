package com.saveo.movie.repo

import androidx.lifecycle.MutableLiveData
import com.saveo.movie.interfaces.NetworkResponseCallback
import com.saveo.movie.models.MovieList
import com.noob.apps.mvvmcountries.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback

    private var mMovieList: MutableLiveData<List<MovieList>> =
        MutableLiveData<List<MovieList>>().apply { value = emptyList() }

    companion object {
        private var mInstance: PictureRepository? = null
        fun getInstance(): PictureRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = PictureRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mMovieCall: Call<List<MovieList>>

    fun getMovieList(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<MovieList>> {
        mCallback = callback
        if (mMovieList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mMovieList
        }
        mMovieCall = RestClient.getInstance().getApiService().getMovies()
        mMovieCall.enqueue(object : Callback<List<MovieList>> {

            override fun onResponse(call: Call<List<MovieList>>, response: Response<List<MovieList>>) {
                mMovieList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<MovieList>>, t: Throwable) {
                mMovieList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mMovieList
    }
}