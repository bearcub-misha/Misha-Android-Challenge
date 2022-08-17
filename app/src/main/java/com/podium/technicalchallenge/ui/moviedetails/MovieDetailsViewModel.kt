package com.podium.technicalchallenge.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieDetails
import com.podium.technicalchallenge.repositories.MoviesRepo
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.ui.LoadingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val moviesRepo: MoviesRepo) : LoadingViewModel() {

    val movieDetailsLD: LiveData<MovieDetails>
        get() = _movieDetailsLD
    private val _movieDetailsLD = MutableLiveData<MovieDetails>()

    fun loadMovieDetails(movieId: Int) {
        _stateLD.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                moviesRepo.getMoviesById(movieId)
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<MovieDetails?> -> {
                    _movieDetailsLD.postValue(result.data)
                    _stateLD.postValue(State.LOADED)
                }
                else -> {
                    _stateLD.postValue(State.ERROR)
                }
            }
        }
    }
}
