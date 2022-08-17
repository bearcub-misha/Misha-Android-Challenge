package com.podium.technicalchallenge.repositories

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.GetMovieQuery
import com.podium.technicalchallenge.GetMoviesByGenreQuery
import com.podium.technicalchallenge.GetMoviesQuery
import com.podium.technicalchallenge.GetTopFiveMoviesQuery
import com.podium.technicalchallenge.entity.Actor
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieDetails

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

    suspend fun getMoviesById(movieId: Int): Result<MovieDetails?> {
        val response = try {
            apiClient.query(GetMovieQuery(movieId)).execute()
        } catch (e: Exception) {
            return Result.Error(java.lang.Exception())
        }

        response.data?.movie?.let {
            return Result.Success(MovieAdapter.adapt(it))
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

        fun adapt(movie: GetMovieQuery.Movie): MovieDetails {
            return MovieDetails(
                title = movie.title,
                rating = movie.voteAverage,
                genres = movie.genres,
                imageUrl = movie.posterPath,
                description = movie.overview,
                directorName = movie.director.name,
                cast = movie.cast.map { actor -> Actor(actor.name) }
            )
        }
    }
}