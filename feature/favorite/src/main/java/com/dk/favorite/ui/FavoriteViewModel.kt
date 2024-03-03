package com.dk.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.GetFavoriteProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val _liveData: MutableLiveData<List<Product>> = MutableLiveData()
) : ViewModel() {

    val liveData: LiveData<List<Product>> get() = _liveData

    init {
        getFavoriteProducts()
    }

    fun getFavoriteProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteProductsUseCase.invoke().collect {
                _liveData.postValue(it)
            }
        }
    }


}