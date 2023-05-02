package com.example.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ActivityDetailedScreenBinding

class DetailedScreen : AppCompatActivity() {
    private lateinit var binding: ActivityDetailedScreenBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent = getIntent()
        val movieId = intent.getIntExtra("MOVIE_ID", 0)
        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getSimilarMovies(movieId)
        viewModel.getSynopsis(movieId)
        viewModel.getRating(movieId)
        viewModel.observeBackgroundImageLiveData().observe(this) { url ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500$url")
                .into(binding.posterImage)
        }
        viewModel.observeRatingLiveData().observe(this) { rating ->
            binding.ratingBar.rating = (rating/2).toFloat()
        }
        viewModel.observeOverViewLiveData().observe(this) { overView ->
            binding.overview.text = overView
        }
        viewModel.observeSimilarMoviesLiveData().observe(this) { movieList ->
            similarMoviesAdapter.setMovieList(movieList)
        }

    }

    private fun prepareRecyclerView() {
        similarMoviesAdapter = SimilarMoviesAdapter()
        binding.similarMoviesRecycler.apply {
            layoutManager = GridLayoutManager(this@DetailedScreen, 2)
            adapter = similarMoviesAdapter
        }
    }
}