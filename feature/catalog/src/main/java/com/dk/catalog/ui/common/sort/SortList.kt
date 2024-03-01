package com.dk.catalog.ui.common.sort

import com.dk.core.catalog.domain.model.Product

class SortList(private val sortingType: SortingType) {

    fun getSorted(list: List<Product>): List<Product> {
        return when (sortingType) {
            SortingType.RATING_DESC ->
                list.sortedByDescending { it.feedback.rating.toFloat() }

            SortingType.PRICE_DESC ->
                list.sortedByDescending { it.price.priceWithDiscount.toInt() }

            SortingType.PRICE_ASC -> list.sortedBy { it.price.priceWithDiscount.toInt() }
        }
    }
}