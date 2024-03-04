package com.dk.core.favorite.domain.usecases

import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class RemoveFromFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {

    suspend operator fun invoke(product: Product): Flow<Boolean> {
        return favoriteRepository.removeFromFavorite(product)
    }
}