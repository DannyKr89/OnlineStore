package com.dk.api.retrofit.data

import android.content.SharedPreferences
import com.dk.api.retrofit.ProductApi
import com.dk.api.retrofit.mapper.toProduct
import com.dk.core.app.utils.FAVORITE_LIST
import com.dk.core.catalog.domain.CatalogRepository
import com.dk.core.catalog.domain.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.flow

class CatalogRepositoryImpl(
    private val api: ProductApi,
    private val sharedPreferences: SharedPreferences
) : CatalogRepository {
    override suspend fun getProducts() = flow {
        val products = api.getProducts().items.map { it.toProduct() }
        val json = sharedPreferences.getString(FAVORITE_LIST, "")
        val favorites: List<Product> =
            Gson().fromJson(json, object : TypeToken<List<Product>>() {}.type) ?: emptyList()
        products.forEach { prod ->
            favorites.forEach { fav ->
                if (prod.id == fav.id) {
                    prod.isFavorite = fav.isFavorite
                }
            }
        }
        emit(products)
    }
}