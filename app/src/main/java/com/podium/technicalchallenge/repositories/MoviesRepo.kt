package com.podium.technicalchallenge.repositories

import com.podium.technicalchallenge.GetMoviesQuery
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.network.ApiClient

class MoviesRepo {

    suspend fun getMovies(): Result<List<MovieEntity>?> {
        val response = try {
            ApiClient.getInstance().provideApolloClient().query(GetMoviesQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> Adapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }

    companion object {
        private var INSTANCE: MoviesRepo? = null
        fun getInstance() = INSTANCE
            ?: MoviesRepo().also {
                INSTANCE = it
            }
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