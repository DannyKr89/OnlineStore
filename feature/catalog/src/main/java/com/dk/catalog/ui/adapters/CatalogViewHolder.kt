package com.dk.catalog.ui.adapters

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dk.catalog.R
import com.dk.catalog.databinding.ItemProductBinding
import com.dk.core.catalog.domain.model.Product

class CatalogViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback


    fun bind(
        product: Product,
        productListener: ((Product) -> Unit)?,
        favoriteListener: ((Product) -> Unit)?
    ) {
        with(binding) {
            val imageAdapter = ImageAdapter()
            val images = getImageList(product.id)
            imageAdapter.submitList(images)
            setupDots(images.size)
            root.setOnClickListener {
                productListener?.invoke(product)
            }
            vpImages.adapter = imageAdapter
            tvProductTitle.text = product.title
            tvProductSubtitle.text = product.subtitle
            tvOldPrice.text =
                StringBuilder().append(product.price.price).append(" ").append(product.price.unit)
            tvDiscountPercent.text =
                StringBuilder().append("-").append(product.price.discount).append("%")
            tvPriceWithDiscount.text =
                StringBuilder().append(product.price.priceWithDiscount).append(" ")
                    .append(product.price.unit)
            tvProductRating.text = product.feedback.rating.toString()
            tvRatingCount.text =
                StringBuilder().append("(").append(product.feedback.count).append(")")
            ivFavorite.setOnClickListener {
                product.isFavorite = !product.isFavorite
                if (product.isFavorite) {
                    ivFavorite.setImageResource(R.drawable.ic_favorite)
                } else {
                    ivFavorite.setImageResource(R.drawable.ic_not_favorite)
                }
                favoriteListener?.invoke(product)
            }
        }
    }

    private fun setupDots(size: Int) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        with(binding) {
            llPage.removeAllViews()
            val images = Array(size) { ImageView(root.context) }
            images.forEach {
                it.setImageResource(R.drawable.non_active_dot)
                llPage.addView(it, params)
            }

            images[0].setImageResource(R.drawable.active_dot)

            pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    images.mapIndexed { index, imageView ->
                        if (position == index) {
                            imageView.setImageResource(R.drawable.active_dot)
                        } else {
                            imageView.setImageResource(R.drawable.non_active_dot)
                        }
                    }
                }
            }
            vpImages.registerOnPageChangeCallback(pageChangeListener)
        }
    }

    private fun getImageList(id: String): List<Int> {
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
}
