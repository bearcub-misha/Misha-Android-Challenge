package com.podium.technicalchallenge.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.podium.technicalchallenge.entity.Genre
import com.podium.technicalchallenge.repositories.GenresRepo
import com.podium.technicalchallenge.repositories.Result
import com.podium.technicalchallenge.ui.LoadingViewModel
import com.podium.technicalchallenge.ui.genres.movies.GenreMoviesFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel(private val genresRepo: GenresRepo) : LoadingViewModel() {

    val genresLD: LiveData<List<Genre>>
        get() = _genresLD
    private val _genresLD = MutableLiveData<List<Genre>>(emptyList())

    fun getGenres() {
        _stateLD.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                genresRepo.getGenres()
            } catch (e: Exception) {
                Result.Error(e)
            }
            when (result) {
                is Result.Success<List<Genre>?> -> {
                    _genresLD.postValue(result.data)
                    _stateLD.postValue(State.LOADED)
                }
                else -> {
                    _stateLD.postValue(State.ERROR)
                }
            }
        }
    }

    fun onGenreSelected(genre: Genre) {
        navigateTo(
            GenresFragmentDirections.actionGenresFragmentToGenreMoviesFragment(
                genre.title
            )
        )
    }
}
