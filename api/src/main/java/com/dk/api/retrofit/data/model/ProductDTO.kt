package com.dk.core.catalog.domain.model

data class ProductDTO(
    val available: Int,
    val description: String,
    val feedback: FeedbackDTO,
    val id: String,
    val info: List<InfoDTO>,
    val ingredients: String,
    val price: PriceDTO,
    val subtitle: String,
    val tags: List<String>,
    val title: String
)