package com.example.movieapp

data class Reviews(
    val id: Int,
    val page: Int,
    val results: ArrayList<ReviewResult>,
    val total_pages: Int,
    val total_results: Int
)