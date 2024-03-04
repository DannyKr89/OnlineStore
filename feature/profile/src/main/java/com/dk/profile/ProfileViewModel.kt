package com.dk.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.usecases.GetFavoriteProductsUseCase
import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.usecase.LoginUseCase
import com.dk.core.login.domain.usecase.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val loginUseCase: LoginUseCase,
    private val _logoutLiveData: MutableLiveData<Boolean> = MutableLiveData(),
    private val _loginLiveData: MutableLiveData<Profile?> = MutableLiveData(),
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val _favoriteLiveData: MutableLiveData<List<Product>> = MutableLiveData()
) : ViewModel() {

    val loginLiveData: LiveData<Profile?> get() = _loginLiveData
    val logoutLiveData: LiveData<Boolean> get() = _logoutLiveData
    val favoriteLiveData: LiveData<List<Product>> get() = _favoriteLiveData

    fun getProfile() {
        viewModelScope.launch {
            loginUseCase.invoke().collect {
                _loginLiveData.postValue(it)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().collect() {
                _logoutLiveData.postValue(it)
            }
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteProductsUseCase.invoke().collect {
                _favoriteLiveData.postValue(it)
            }
        }
    }
}