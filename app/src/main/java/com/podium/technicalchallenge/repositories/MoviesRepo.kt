package com.podium.technicalchallenge.repositories

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.GetMoviesByGenreQuery
import com.podium.technicalchallenge.GetMoviesQuery
import com.podium.technicalchallenge.GetTopFiveMoviesQuery
import com.podium.technicalchallenge.entity.Genre
import com.podium.technicalchallenge.entity.Movie

class MoviesRepo(private val apiClient: ApolloClient) {

    suspend fun getTopFiveMovies(): Result<List<Movie>?> {
        val response = try {
            apiClient.query(GetTopFiveMoviesQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> MovieAdapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }

    suspend fun getMovies(): Result<List<Movie>?> {
        val response = try {
            apiClient.query(GetMoviesQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> MovieAdapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }

    suspend fun getMoviesByGenre(genre: String): Result<List<Movie>?> {
        val response = try {
            apiClient.query(GetMoviesByGenreQuery(genre)).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> MovieAdapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }
}

private class MovieAdapter {
    companion object {
        fun adaptOrNull(movie: GetMoviesByGenreQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    movie.id,
                    movie.title,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }

        fun adaptOrNull(movie: GetMoviesQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    movie.id,
                    movie.title,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }

        fun adaptOrNull(movie: GetTopFiveMoviesQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    movie.id,
                    movie.title,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }
    }
}