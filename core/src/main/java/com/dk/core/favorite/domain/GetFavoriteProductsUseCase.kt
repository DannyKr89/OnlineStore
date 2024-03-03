package com.dk.core.favorite.domain

import com.dk.core.catalog.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductsUseCase(private val favoriteRepository: FavoriteRepository) {

    suspend operator fun invoke(): Flow<List<Product>> {
        return favoriteRepository.getFavoriteProducts()
    }
}