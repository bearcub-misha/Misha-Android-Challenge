package com.podium.technicalchallenge.entity

data class MovieDetails(
    val title: String,
    val directorName: String,
    val imageUrl: String?,
    val rating: Double,
    val genres: List<String>,
    val description: String,
    val cast: List<Actor>,
)

data class Actor(
    val name: String
)

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String?
)

data class Genre(
    val title: String
)

