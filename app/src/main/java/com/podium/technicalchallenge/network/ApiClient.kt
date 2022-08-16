package com.podium.technicalchallenge.network

import com.apollographql.apollo3.ApolloClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiClient {
    val API_URL = "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/"

    companion object {
        private var INSTANCE: ApiClient? = null
        fun getInstance() = INSTANCE
            ?: ApiClient().also {
                INSTANCE = it
            }
    }

    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("$API_URL/graphql")
            .build()
    }

}
