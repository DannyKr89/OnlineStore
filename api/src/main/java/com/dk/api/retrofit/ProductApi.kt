package com.dk.api.retrofit

import com.dk.core.catalog.domain.model.ProductsDTO
import retrofit2.http.GET

interface ProductApi {

    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProducts(): ProductsDTO
}