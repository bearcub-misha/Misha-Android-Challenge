package com.podium.technicalchallenge.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.repositories.MoviesRepo

class DashboardViewModel : ViewModel() {

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
                MoviesRepo.getInstance().getMovies()
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
        Log.d("Misha", "Selected!!! ${movie.title}")
    }

    enum class State {
        LOADING, LOADED, ERROR
    }
}
