package com.dk.api.retrofit.data

import com.dk.api.retrofit.ProductApi
import com.dk.api.retrofit.mapper.toProduct
import com.dk.core.catalog.domain.CatalogRepository
import kotlinx.coroutines.flow.flow

class CatalogRepositoryImpl(private val api: ProductApi) : CatalogRepository {
    override suspend fun getProducts() = flow {
        val products = api.getProducts().items.map { it.toProduct() }
        emit(products)
    }
}