package com.dk.core.favorite.domain.usecases

import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.repository.FavoriteRepository

class AddToFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {

    suspend operator fun invoke(product: Product) {
        favoriteRepository.addToFavorite(product)
    }
}