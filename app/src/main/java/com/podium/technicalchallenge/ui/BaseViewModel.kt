package com.podium.technicalchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDirections

open class BaseViewModel: ViewModel() {

    val navigationEvent = SingleLiveEvent<NavController.() -> Any>()

    fun navigateTo(directions: NavDirections) {
        withNavController { navigate(directions) }
    }

    private fun withNavController(block: NavController.() -> Any) {
        navigationEvent.postValue(block)
    }

}
