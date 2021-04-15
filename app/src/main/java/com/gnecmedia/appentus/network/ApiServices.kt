package com.noob.apps.mvvmcountries.network

import com.gnecmedia.appentus.models.Picture
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

/*    @GET("all")
    fun getCountries() : Call<List<Picture>>*/

    @GET("list")
    fun getPictures() : Call<List<Picture>>
}