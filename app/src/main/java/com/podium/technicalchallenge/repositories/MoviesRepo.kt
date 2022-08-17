package com.podium.technicalchallenge.repositories

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.GetMoviesQuery
import com.podium.technicalchallenge.entity.MovieEntity

class MoviesRepo(private val apiClient: ApolloClient) {

    suspend fun getMovies(): Result<List<MovieEntity>?> {
        val response = try {
            apiClient.query(GetMoviesQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> Adapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }
}

private class Adapter {
    companion object {
        fun adaptOrNull(movie: GetMoviesQuery.Movie?): MovieEntity? {
            movie?.let {
                return MovieEntity(
                    movie.title,
                    movie.releaseDate,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }
    }
}