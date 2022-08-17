package com.podium.technicalchallenge.di

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.ui.dashboard.DashboardViewModel
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

    viewModel { DashboardViewModel(get()) }
}