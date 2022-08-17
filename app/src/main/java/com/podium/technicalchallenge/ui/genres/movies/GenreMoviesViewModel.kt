package com.podium.technicalchallenge.ui.genres.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.Movie
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.ui.LoadingViewModel
import com.podium.technicalchallenge.ui.common.view.SortDirection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenreMoviesViewModel(private val moviesRepo: MoviesRepo) : LoadingViewModel() {

    val moviesLD: LiveData<List<Movie>>
        get() = _moviesLD
    private val _moviesLD = MutableLiveData<List<Movie>>(emptyList())

    fun getMovies(genre: String, sortBy: String, direction: SortDirection) {
        _stateLD.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                moviesRepo.getMoviesByGenre(genre, sortBy, direction)
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<Movie>?> -> {
                    _moviesLD.postValue(result.data)
                    _stateLD.postValue(State.LOADED)
                }
                else -> {
                    _stateLD.postValue(State.ERROR)
                }
            }
        }
    }

    fun onMovieSelected(movie: Movie) {
        navigateTo(
            GenreMoviesFragmentDirections.actionGenreMoviesFragmentToMovieDetailsFragment(
                movie.id
            )
        )
    }
}
