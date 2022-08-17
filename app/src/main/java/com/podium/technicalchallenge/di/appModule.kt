package com.podium.technicalchallenge.di

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.repositories.GenresRepo
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.ui.allmovies.AllMoviesViewModel
import com.podium.technicalchallenge.ui.genres.GenresViewModel
import com.podium.technicalchallenge.ui.genres.movies.GenreMoviesViewModel
import com.podium.technicalchallenge.ui.moviedetails.MovieDetailsViewModel
import com.podium.technicalchallenge.ui.topmovies.TopMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val API_URL =
    "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql"

val appModule = module {
    single {
        ApolloClient.Builder()
            .serverUrl(API_URL)
            .build()
    }

    single { MoviesRepo(get()) }
    single { GenresRepo(get()) }

    viewModel { TopMoviesViewModel(get()) }
    viewModel { GenresViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { AllMoviesViewModel(get()) }
    viewModel { GenreMoviesViewModel(get()) }
}