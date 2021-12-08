package com.noob.apps.mvvmcountries.network

import com.saveo.movie.models.MovieList
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("1933c33e-7251-4c3f-ad1f-32fa3f34446e")
    fun getMovies() : Call<List<MovieList>>
}