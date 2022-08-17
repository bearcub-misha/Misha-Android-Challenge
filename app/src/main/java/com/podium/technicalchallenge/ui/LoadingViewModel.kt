package com.podium.technicalchallenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections

open class LoadingViewModel : BaseViewModel() {

    val stateLD: LiveData<State>
        get() = _stateLD
    protected val _stateLD = MutableLiveData(State.LOADING)

    enum class State {
        LOADING, LOADED, ERROR
    }
}
