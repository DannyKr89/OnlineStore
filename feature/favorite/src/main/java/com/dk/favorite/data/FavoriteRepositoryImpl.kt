package com.dk.favorite.data

import android.content.SharedPreferences
import com.dk.core.app.utils.FAVORITE_LIST
import com.dk.core.catalog.domain.model.Product
import com.dk.core.favorite.domain.FavoriteRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    FavoriteRepository {

    private val list = hashSetOf<Product>()

    override suspend fun getFavoriteProducts() = flow {
        val json = sharedPreferences.getString(FAVORITE_LIST, "")
        list.clear()
        list.addAll(
            Gson().fromJson(json, object : TypeToken<List<Product>>() {}.type) ?: emptyList()
        )

        emit(list.toMutableList().asReversed())
    }

    override suspend fun addToFavorite(product: Product) {
        synchronized(list) {
            list.add(product)
        }
        val list = Gson().toJson(list)
        sharedPreferences.edit().apply {
            putString(FAVORITE_LIST, list)
        }.apply()
    }

    override suspend fun removeFromFavorite(product: Product) = flow {
        try {
            synchronized(list) {
                product.isFavorite = true
                list.remove(product)
            }
            product.isFavorite = false
            val list = Gson().toJson(list)
            sharedPreferences.edit().apply {
                putString(FAVORITE_LIST, list)
            }.apply()
            emit(true)
        } catch (_: Exception) {
            emit(false)
        }

    }
}