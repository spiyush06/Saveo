package com.gnecmedia.appentus.viewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gnecmedia.appentus.interfaces.NetworkResponseCallback
import com.gnecmedia.appentus.models.Picture
import com.gnecmedia.appentus.repo.PictureRepository
import com.gnecmedia.appentus.utils.NetworkHelper

class PictureListViewModel(private val app: Application) : AndroidViewModel(app) {
    private var mList: MutableLiveData<List<Picture>> =
        MutableLiveData<List<Picture>>().apply { value = emptyList() }
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = PictureRepository.getInstance()

    fun fetchCountriesFromServer(forceFetch: Boolean): MutableLiveData<List<Picture>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            mList = mRepository.getCountries(object : NetworkResponseCallback {
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

    fun onRefreshClicked(view: View) {
        fetchCountriesFromServer(true)
    }
}