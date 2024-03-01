package com.dk.core.catalog.utils

fun getImageList(id: String): List<Int> {
    return when (id) {
        "cbf0c984-7c6c-4ada-82da-e29dc698bb50" -> {
            listOf(com.dk.core.R.drawable.image6, com.dk.core.R.drawable.image5)
        }

        "54a876a5-2205-48ba-9498-cfecff4baa6e" -> {
            listOf(com.dk.core.R.drawable.image1, com.dk.core.R.drawable.image2)
        }

        "75c84407-52e1-4cce-a73a-ff2d3ac031b3" -> {
            listOf(com.dk.core.R.drawable.image5, com.dk.core.R.drawable.image6)
        }

        "16f88865-ae74-4b7c-9d85-b68334bb97db" -> {
            listOf(com.dk.core.R.drawable.image3, com.dk.core.R.drawable.image4)
        }

        "26f88856-ae74-4b7c-9d85-b68334bb97db" -> {
            listOf(com.dk.core.R.drawable.image2, com.dk.core.R.drawable.image3)
        }

        "15f88865-ae74-4b7c-9d81-b78334bb97db" -> {
            listOf(com.dk.core.R.drawable.image6, com.dk.core.R.drawable.image1)
        }

        "88f88865-ae74-4b7c-9d81-b78334bb97db" -> {
            listOf(com.dk.core.R.drawable.image4, com.dk.core.R.drawable.image3)
        }

        else -> {
            listOf(com.dk.core.R.drawable.image1, com.dk.core.R.drawable.image5)
        }
    }
}