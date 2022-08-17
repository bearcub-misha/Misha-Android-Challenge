package com.podium.technicalchallenge.repositories

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.GetGenresQuery
import com.podium.technicalchallenge.entity.Genre

class GenresRepo(private val apiClient: ApolloClient) {

    suspend fun getGenres(): Result<List<Genre>?> {
        val response = try {
            apiClient.query(GetGenresQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(e)
        }

        response.data?.let {
            return Result.Success(it.genres?.mapNotNull { genre -> GenreAdapter.adaptOrNull(genre) })
        }
        return Result.Error(java.lang.Exception())
    }

}

private class GenreAdapter {
    companion object {
        fun adaptOrNull(genre: String?): Genre? {
            genre?.let {
                return Genre(it)
            }
            return null
        }
    }
}