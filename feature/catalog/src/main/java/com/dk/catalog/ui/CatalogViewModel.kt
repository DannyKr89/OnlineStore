package com.dk.catalog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.core.catalog.domain.GetProductsUseCase
import com.dk.core.catalog.domain.model.Product
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val _liveData: MutableLiveData<List<Product>> = MutableLiveData()
) : ViewModel() {

    val liveData: LiveData<List<Product>> get() = _liveData

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase.invoke().collect() { list ->
                _liveData.postValue(list)
            }
        }
    }
}