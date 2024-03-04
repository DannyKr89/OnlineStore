package com.dk.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.usecase.SaveProfileUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val saveProfileUseCase: SaveProfileUseCase
) : ViewModel() {

    fun saveProfile(firstName: String, secondName: String, phoneNumber: String) {
        val profile = Profile(firstName, secondName, phoneNumber)
        viewModelScope.launch() {
            saveProfileUseCase.invoke(profile)
        }
    }
}