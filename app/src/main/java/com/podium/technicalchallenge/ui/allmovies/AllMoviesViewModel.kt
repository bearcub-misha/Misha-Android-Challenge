package com.podium.technicalchallenge.ui.allmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.ui.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllMoviesViewModel(private val moviesRepo: MoviesRepo) : LoadingViewModel() {

    val moviesLD: LiveData<List<MovieEntity>>
        get() = _moviesLD
    private val _moviesLD = MutableLiveData<List<MovieEntity>>(emptyList())

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
        navigateTo(AllMoviesFragmentDirections.actionAllMoviesFragmentToMovieDetailsFragment(movie.id))
    }
}
