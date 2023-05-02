package com.example.movieapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("now_playing?")
    fun getMovieData(@Query("api_key") api_key: String): Call<MovieData>

    @GET("{id}")
    fun getMovieSynopsis(@Path("id") movieId: Int, @Query("api_key") api_key: String): Call<Synopsis>

    @GET("{id}/reviews")
    fun getMovieReview(@Path("id") movieId: Int, @Query("api_key") api_key: String): Call<Reviews>

    @GET("{id}/credits")
    fun getMovieCredits(@Path("id") movieId: String, @Query("api_key") api_key: String): Call<MovieData>

    @GET("{id}/similar")
    fun getSimilarMovies(@Path("id") movieId: Int, @Query("api_key") api_key: String): Call<MovieData>
}