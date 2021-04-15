package com.gnecmedia.appentus.repo

import androidx.lifecycle.MutableLiveData
import com.gnecmedia.appentus.interfaces.NetworkResponseCallback
import com.gnecmedia.appentus.models.Picture
import com.noob.apps.mvvmcountries.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private var mCountryList: MutableLiveData<List<Picture>> =
        MutableLiveData<List<Picture>>().apply { value = emptyList() }

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


    private lateinit var mCountryCall: Call<List<Picture>>

    fun getCountries(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<List<Picture>> {
        mCallback = callback
        if (mCountryList.value!!.isNotEmpty() && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mCountryList
        }
        mCountryCall = RestClient.getInstance().getApiService().getPictures()
        mCountryCall.enqueue(object : Callback<List<Picture>> {

            override fun onResponse(call: Call<List<Picture>>, response: Response<List<Picture>>) {
                mCountryList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<Picture>>, t: Throwable) {
                mCountryList.value = emptyList()
                mCallback.onNetworkFailure(t)
            }

        })
        return mCountryList
    }
}