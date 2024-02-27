package com.dk.core.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val _loginLiveData: MutableLiveData<Profile?> = MutableLiveData(),
    private val loginUseCase: LoginUseCase,

    ) : ViewModel() {
    val loginLiveData: LiveData<Profile?> get() = _loginLiveData

    fun login() {
        viewModelScope.launch() {
            loginUseCase.invoke().collect {
                _loginLiveData.postValue(it)
            }
        }
    }
}