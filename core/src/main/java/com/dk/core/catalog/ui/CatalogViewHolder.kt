package com.dk.core.catalog.ui

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dk.core.R
import com.dk.core.catalog.domain.model.Product
import com.dk.core.catalog.utils.getImageList
import com.dk.core.databinding.ItemProductBinding

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
            if (product.feedback == null) {
                tvRatingCount.isVisible = false
                tvProductRating.isVisible = false
            } else {
                tvRatingCount.isVisible = true
                tvProductRating.isVisible = true
            }
            tvProductRating.text = product.feedback?.rating.toString()
            tvRatingCount.text =
                StringBuilder().append("(").append(product.feedback?.count).append(")")
            checkIsFavorite(product.isFavorite)
            ivFavorite.setOnClickListener {
                favoriteListener?.invoke(product)
                product.isFavorite = !product.isFavorite
                checkIsFavorite(product.isFavorite)
            }
        }
    }

    private fun checkIsFavorite(isFavorite: Boolean) {
        with(binding) {
            when (isFavorite) {
                true -> ivFavorite.setImageResource(R.drawable.ic_favorite)
                false -> ivFavorite.setImageResource(R.drawable.ic_not_favorite)
            }
        }
    }

    private fun setupDots(size: Int) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(6, 0, 6, 0)
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
}
