package com.dk.core.catalog.domain.model

data class PriceDTO(
    val discount: Int,
    val price: String,
    val priceWithDiscount: String,
    val unit: String
)