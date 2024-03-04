package com.dk.core.catalog.domain

import com.dk.core.catalog.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    suspend fun getProducts(): Flow<List<Product>>
}