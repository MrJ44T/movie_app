package com.example.movieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.SingleItemScreenBinding

class MovieAdapter(private val recyclerViewItemClick: RecyclerViewItemClick) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList: ArrayList<Result> = ArrayList()

    fun setMovieList(movieList : ArrayList<Result>){
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: SingleItemScreenBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SingleItemScreenBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.movieName.text = movieList[position].title
        holder.binding.movieReleaseDate.text = buildString {
        append("Release-Date: ")
        append(movieList[position].release_date)
    }

        // Glide Library for image
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500"+ movieList[position].poster_path)
            .into(holder.binding.moviePoster)

        holder.binding.cardView.setOnClickListener {
            recyclerViewItemClick.onItemClick(position, movieId = movieList[position].id)
        }
    }
}