package com.podium.technicalchallenge.repositories

import com.apollographql.apollo3.ApolloClient
import com.podium.technicalchallenge.GetMovieQuery
import com.podium.technicalchallenge.GetMoviesByGenreQuery
import com.podium.technicalchallenge.GetMoviesQuery
import com.podium.technicalchallenge.GetTopFiveMoviesQuery
import com.podium.technicalchallenge.entity.Actor
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.entity.MovieDetails
import com.podium.technicalchallenge.type.Sort
import com.podium.technicalchallenge.ui.common.view.SortDirection

class MoviesRepo(private val apiClient: ApolloClient) {

    suspend fun getTopFiveMovies(): Result<List<Movie>?> {
        val response = try {
            apiClient.query(GetTopFiveMoviesQuery()).execute()
        } catch (e: Exception) {
            return Result.Error(e)
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> MovieAdapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }

    suspend fun getMovies(sortBy: String, direction: SortDirection): Result<List<Movie>?> {
        val response = try {
            val moviesSort = MoviesSort.valueOf(sortBy)
            val direction = Sort.valueOf(direction.toString())
            apiClient.query(GetMoviesQuery(moviesSort.propertyName, direction)).execute()
        } catch (e: Exception) {
            return Result.Error(e)
        }

        response.data?.let {
            return Result.Success(it.movies?.mapNotNull { movie -> MovieAdapter.adaptOrNull(movie) })
        }
        return Result.Error(java.lang.Exception())
    }

    suspend fun getMoviesByGenre(genre: String, sortBy: String, direction: SortDirection): Result<List<Movie>?> {
        val response = try {
            val moviesSort = MoviesSort.valueOf(sortBy)
            val direction = Sort.valueOf(direction.toString())
            apiClient.query(GetMoviesByGenreQuery(genre, moviesSort.propertyName, direction)).execute()
        } catch (e: Exception) {
            return Result.Error(e)
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
            return Result.Error(e)
        }

        response.data?.movie?.let {
            return Result.Success(MovieAdapter.adapt(it))
        }
        return Result.Error(java.lang.Exception())
    }
}

enum class MoviesSort(val propertyName: String) {
    TITLE("title"),
    POPULARITY("voteAverage")
}

private class MovieAdapter {
    companion object {
        fun adaptOrNull(movie: GetMoviesByGenreQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    id = movie.id,
                    title = movie.title,
                    rating = movie.voteAverage,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }

        fun adaptOrNull(movie: GetMoviesQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    id = movie.id,
                    title = movie.title,
                    rating = movie.voteAverage,
                    imageUrl = movie.posterPath
                )
            }
            return null
        }

        fun adaptOrNull(movie: GetTopFiveMoviesQuery.Movie?): Movie? {
            movie?.let {
                return Movie(
                    id = movie.id,
                    title = movie.title,
                    rating = movie.voteAverage,
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