package com.example.movieapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RecyclerViewItemClick{
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getMovies()
        viewModel.observeMovieLiveData().observe(this) { movieList ->
            movieAdapter.setMovieList(movieList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Type here to search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getFilterMovies(newText)
                viewModel.observeMovieFilterLiveData().observe(this@MainActivity) {
                    movieAdapter.setMovieList(it)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
    }

    override fun onItemClick(position: Int, movieId: Int) {
        Log.d("MovieAppp: ", "$position and $movieId")
        val intent = Intent(this@MainActivity, DetailedScreen::class.java)
        intent.putExtra("MOVIE_ID", movieId)
        startActivity(intent)
    }
}