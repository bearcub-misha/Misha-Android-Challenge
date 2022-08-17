package com.podium.technicalchallenge.ui.topmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.ui.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopMoviesViewModel(private val moviesRepo: MoviesRepo) : BaseViewModel() {

    val moviesLD: LiveData<List<MovieEntity>>
        get() = _moviesLD
    private val _moviesLD = MutableLiveData<List<MovieEntity>>(emptyList())

    val stateLD: LiveData<State>
        get() = _stateLD
    private val _stateLD = MutableLiveData(State.LOADING)

    fun getMovies() {
        _stateLD.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                moviesRepo.getMovies()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<MovieEntity>?> -> {
                    _moviesLD.postValue(result.data)
                    _stateLD.postValue(State.LOADED)
                }
                else -> {
                    _stateLD.postValue(State.ERROR)
                }
            }
        }
    }

    fun onMovieSelected(movie: MovieEntity) {
        navigateTo(TopMoviesFragmentDirections.actionTopMoviesFragmentToMovieDetailsFragment(movie.id))
    }

    enum class State {
        LOADING, LOADED, ERROR
    }
}
