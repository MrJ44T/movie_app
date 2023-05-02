package com.example.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.SimilarSingleItemBinding

class SimilarMoviesAdapter: RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>() {
    private var similarMovies: ArrayList<Result> = ArrayList()
    fun setMovieList(movieList : ArrayList<Result>){
        this.similarMovies = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: SimilarSingleItemBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SimilarSingleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return similarMovies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movieName.text = similarMovies[position].title

        // Glide Library for image
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500"+ similarMovies[position].poster_path)
            .into(holder.binding.moviePoster)
    }
}