package com.dk.core.favorite.domain

import com.dk.core.catalog.domain.model.Product

class AddToFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {

    suspend operator fun invoke(product: Product) {
        favoriteRepository.addToFavorite(product)
    }
}