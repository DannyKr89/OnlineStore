package com.dk.core.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.usecases.AddToFavoriteUseCase
import com.dk.core.favorite.domain.usecases.RemoveFromFavoriteUseCase
import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val _loginLiveData: MutableLiveData<Profile?> = MutableLiveData(),
    private val _productLiveData: MutableLiveData<Product?> = MutableLiveData(),
    private val _removeLiveData: MutableLiveData<Boolean> = MutableLiveData(),
    private val loginUseCase: LoginUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase

) : ViewModel() {
    val loginLiveData: LiveData<Profile?> get() = _loginLiveData
    val productLiveData: LiveData<Product?> get() = _productLiveData
    val removeLiveData: LiveData<Boolean> get() = _removeLiveData

    fun login() {
        viewModelScope.launch() {
            loginUseCase.invoke().collect {
                _loginLiveData.postValue(it)
            }
        }
    }

    fun product(product: Product?) {
        _productLiveData.value = product
    }

    fun addToFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            addToFavoriteUseCase.invoke(product)
        }
    }

    fun removeFromFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromFavoriteUseCase.invoke(product).collect {
                _removeLiveData.postValue(it)
            }
        }
    }
}