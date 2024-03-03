package com.dk.core.favorite.domain

import com.dk.core.catalog.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun getFavoriteProducts(): Flow<List<Product>>

    suspend fun addToFavorite(product: Product)

    suspend fun removeFromFavorite(product: Product): Flow<Boolean>
}