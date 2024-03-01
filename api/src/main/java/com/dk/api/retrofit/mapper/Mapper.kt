package com.dk.api.retrofit.mapper

import com.dk.core.catalog.domain.model.Feedback
import com.dk.core.catalog.domain.model.FeedbackDTO
import com.dk.core.catalog.domain.model.Info
import com.dk.core.catalog.domain.model.InfoDTO
import com.dk.core.catalog.domain.model.Price
import com.dk.core.catalog.domain.model.PriceDTO
import com.dk.core.catalog.domain.model.Product
import com.dk.core.catalog.domain.model.ProductDTO

fun ProductDTO.toProduct(): Product {
    return Product(
        available = available,
        description = description,
        feedback = feedback.toFeedback(),
        id = id,
        info = info.map { it.toInfo() },
        ingredients = ingredients,
        price = price.toPrice(),
        subtitle = subtitle,
        tags = tags,
        title = title
    )
}

fun FeedbackDTO.toFeedback(): Feedback {
    return Feedback(
        count = count,
        rating = rating
    )
}

fun InfoDTO.toInfo(): Info {
    return Info(
        title = title,
        value = value
    )
}

fun PriceDTO.toPrice(): Price {
    return Price(
        discount = discount,
        price = price,
        priceWithDiscount = priceWithDiscount,
        unit = unit
    )
}