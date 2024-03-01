package com.dk.core.catalog.domain

import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val catalogRepository: CatalogRepository) {

    suspend operator fun invoke(): Flow<List<com.dk.core.catalog.domain.model.Product>> {
        return catalogRepository.getProducts()
    }
}