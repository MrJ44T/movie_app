package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {
    private var movieLiveData: MutableLiveData<ArrayList<Result>> = MutableLiveData<ArrayList<Result>>()
    private var movieFilterLiveData: MutableLiveData<ArrayList<Result>> = MutableLiveData<ArrayList<Result>>()
    private var similarMoviesLiveData: MutableLiveData<ArrayList<Result>> = MutableLiveData<ArrayList<Result>>()
    private var overViewLiveData: MutableLiveData<String> = MutableLiveData()
    private var movieRatingLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    private var movieReviews: ArrayList<ReviewResult> = ArrayList()
    private var backgroundImageLiveData: MutableLiveData<String> = MutableLiveData()
    private var movieName: ArrayList<Result> = ArrayList()

    fun getMovies() {
        RetrofitInstance.api.getMovieData("fe410d60e6a4f6b070d5b512fab7e226").enqueue(object :
            Callback<MovieData>{
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if (response.body() == null) {
                    Log.d("MovieApp: ", "No data present")
                }
                else {
                    Log.d("MovieApp: ", response.body().toString())
                    movieLiveData.value = response.body()!!.results
                    for (i in 0 until movieLiveData.value!!.size) {
                        movieName.add(movieLiveData.value!![i])
                    }
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("MovieApp: ", t.toString())
            }
        })
    }

    fun getFilterMovies(text: String) {
        movieName.clear()
        for (i in 0 until movieLiveData.value!!.size) {
            val str = movieLiveData.value!![i].title
            Log.d("MovieApp: ", "Str is $str and text is $text")
            if (isStringMatching(str.lowercase(), text.lowercase())) {
                movieName.add(movieLiveData.value!![i])
            }
            else Log.d("MovieApp: ", "isStringMatching is false")
        }

        if (text == "") movieFilterLiveData.value = movieLiveData.value
        else movieFilterLiveData.value = movieName
        Log.d("MovieApp: ", movieFilterLiveData.value!!.toString())
    }

    private fun isStringMatching(str: String, text: String): Boolean {
        val n = text.length
        val m = str.length
        if(m < n) return false
        else if (m == n) return str == text
        else {
            for (i in 0 .. m-n) {
                if (i == 0 && str.substring(0, n) == text) return true
                else
                {
                    if(str[i].equals(' ', false) && i+n+1 <= m && str.substring(i+1, i+n+1) == text) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun getSimilarMovies(movieId: Int) {
        RetrofitInstance.api.getSimilarMovies(movieId,"fe410d60e6a4f6b070d5b512fab7e226").enqueue(object :
            Callback<MovieData>{
            override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                if (response.body() == null) {
                    Log.d("MovieApp2: ", "No data present")
                }
                else {
                    Log.d("MovieApp2: ", response.body().toString())
                    similarMoviesLiveData.value = response.body()!!.results
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                Log.d("MovieApp2: ", t.toString())
            }
        })
    }

    fun getSynopsis(id: Int) {
        RetrofitInstance.api.getMovieSynopsis(id,"fe410d60e6a4f6b070d5b512fab7e226").enqueue(object :
            Callback<Synopsis>{
            override fun onResponse(call: Call<Synopsis>, response: Response<Synopsis>) {
                if (response.body() == null) {
                    Log.d("MovieApp2: ", "No data present")
                }
                else {
                    Log.d("MovieApp2: ", response.body().toString())
                    overViewLiveData.value = response.body()!!.overview
                    backgroundImageLiveData.value = response.body()!!.backdrop_path
                }
            }

            override fun onFailure(call: Call<Synopsis>, t: Throwable) {
                Log.d("MovieApp2: ", t.toString())
            }
        })
    }

    fun getRating(id: Int) {
        RetrofitInstance.api.getMovieReview(id,"fe410d60e6a4f6b070d5b512fab7e226").enqueue(object :
            Callback<Reviews>{
            override fun onResponse(call: Call<Reviews>, response: Response<Reviews>) {
                if (response.body() == null) {
                    Log.d("MovieApp2: ", "No data present")
                }
                else {
                    Log.d("MovieApp2: ", response.body().toString())
                    movieReviews = response.body()!!.results
                    movieRatingLiveData.value = movieReviews[0].author_details.rating
                }
            }

            override fun onFailure(call: Call<Reviews>, t: Throwable) {
                Log.d("MovieApp2: ", t.toString())
            }
        })
    }

    fun observeMovieLiveData() : LiveData<ArrayList<Result>> {
        return movieLiveData
    }

    fun observeSimilarMoviesLiveData() : LiveData<ArrayList<Result>> {
        return similarMoviesLiveData
    }

    fun observeOverViewLiveData() : LiveData<String> {
        return overViewLiveData
    }

    fun observeRatingLiveData() : LiveData<Int> {
        return movieRatingLiveData
    }

    fun observeBackgroundImageLiveData() : LiveData<String> {
        return backgroundImageLiveData
    }

    fun observeMovieFilterLiveData() : LiveData<ArrayList<Result>> {
        return movieFilterLiveData
    }
}