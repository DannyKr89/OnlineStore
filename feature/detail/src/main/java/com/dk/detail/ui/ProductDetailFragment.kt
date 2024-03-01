package com.dk.detail.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.MenuProvider
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.dk.core.app.MainViewModel
import com.dk.core.catalog.domain.model.Product
import com.dk.core.catalog.ui.ImageAdapter
import com.dk.core.catalog.utils.getImageList
import com.dk.core.databinding.ItemSpecsBinding
import com.dk.detail.R
import com.dk.detail.databinding.FragmentProductDetailBinding
import org.koin.android.ext.android.get
import kotlin.math.roundToInt

class ProductDetailFragment : Fragment(), MenuProvider {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding: FragmentProductDetailBinding get() = _binding!!
    private val adapter: ImageAdapter = get()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private var isExpandStructure = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        initViewModel()
    }

    private fun initViewModel() {
        mainViewModel.productLiveData.observe(viewLifecycleOwner) {
            showData(it)
        }
    }

    private fun showData(product: Product?) {
        with(binding) {
            product?.let { product ->
                val images = getImageList(product.id)
                adapter.submitList(images)
                vpImages.adapter = adapter
                setupDots(images.size)
                tvProductTitle.text = product.title
                tvProductSubtitle.text = product.subtitle
                setupAvailable(product)
                setupRating(product)
                tvPriceWithDiscount.text =
                    StringBuilder(product.price.priceWithDiscount).append(product.price.unit)
                tvOldPrice.text = StringBuilder(product.price.price).append(product.price.unit)
                tvDiscountPercent.text =
                    StringBuilder().append("-").append(product.price.discount).append("%")
                btnBrand.text = product.title
                tvProductDescription.text = product.description
                btnShowHideDescription.setOnClickListener {
                    showHideDescription()
                }
                setupInfo(product)
                tvProductStructure.text = product.ingredients
                btnShowHideStructure.setOnClickListener {
                    showHideStructure()
                }
                tvPriceWithDiscountInBtn.text =
                    StringBuilder(product.price.priceWithDiscount).append(product.price.unit)
                tvOldPriceInBtn.text = StringBuilder(product.price.price).append(product.price.unit)
            }
        }
    }

    private fun setupInfo(product: Product) {
        with(binding) {
            product.info.forEach {
                val view = LayoutInflater.from(binding.root.context)
                    .inflate(com.dk.core.R.layout.item_specs, llSpecs, false)
                val viewBinding = ItemSpecsBinding.bind(view)
                viewBinding.apply {
                    tvSpecsTitle.text = it.title
                    tvSpecsValue.text = it.value
                    llSpecs.addView(view)
                }
            }
        }
    }

    private fun setupRating(product: Product) {
        with(binding) {
            val rating = product.feedback?.rating
            rating?.let {
                val fullStars = rating.roundToInt() - 1
                val halfStar = it % 1 >= 0.5
                llRating.forEachIndexed { index, view ->
                    if (index < fullStars) {
                        (view as ImageView).setImageResource(R.drawable.ic_full_star)
                    } else if (index == fullStars && halfStar) {
                        (view as ImageView).setImageResource(R.drawable.ic_half_star)
                    } else if (index > fullStars && index < 5) {
                        (view as ImageView).setImageResource(R.drawable.ic_empty_star)
                    }
                }
            }
            tvRating.text = rating.toString()
            val count = product.feedback?.count ?: 0
            tvRatingCount.text = StringBuilder(count.toString()).append(
                when {
                    count % 10 == 1 && count % 100 != 11 -> " отзыв"
                    count % 10 in 2..4 && count % 100 !in 12..14 -> " отзыва"
                    else -> " отзывов"
                }
            )
        }
    }

    private fun setupAvailable(product: Product) {
        with(binding) {
            tvAvailable.text = product.available.toString()
            tvAvailable.text =
                StringBuilder().append("Доступно для заказа ").append(product.available).append(
                    when {
                        product.available % 10 == 1 && product.available % 100 != 11 -> " штука"
                        product.available % 10 in 2..4 && product.available % 100 !in 12..14 -> " штуки"
                        else -> " штук"
                    }
                )
        }
    }

    private fun showHideStructure() {
        with(binding) {
            isExpandStructure = !isExpandStructure
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            if (isExpandStructure) {
                tvProductStructure.maxLines = 6
                btnShowHideStructure.text = resources.getString(com.dk.core.R.string.show_text)
            } else {
                tvProductStructure.maxLines = 2
                btnShowHideStructure.text = resources.getString(com.dk.core.R.string.hide_text)
            }
        }
    }

    private fun showHideDescription() {
        with(binding) {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            btnBrand.isVisible = !btnBrand.isVisible
            tvProductDescription.isVisible = !tvProductDescription.isVisible
            if (btnBrand.isVisible) {
                btnShowHideDescription.text = resources.getString(com.dk.core.R.string.hide_text)
            } else {
                btnShowHideDescription.text = resources.getString(com.dk.core.R.string.show_text)
            }
        }
    }

    private fun setupDots(size: Int) {
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        with(binding) {
            llPage.removeAllViews()
            val images = Array(size) { ImageView(root.context) }
            images.forEach {
                it.setImageResource(com.dk.core.R.drawable.non_active_dot)
                llPage.addView(it, params)
            }

            images[0].setImageResource(com.dk.core.R.drawable.active_dot)

            pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    images.mapIndexed { index, imageView ->
                        if (position == index) {
                            imageView.setImageResource(com.dk.core.R.drawable.active_dot)
                        } else {
                            imageView.setImageResource(com.dk.core.R.drawable.non_active_dot)
                        }
                    }
                }
            }
            vpImages.registerOnPageChangeCallback(pageChangeListener)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = false

    override fun onDestroyView() {
        binding.vpImages.unregisterOnPageChangeCallback(pageChangeListener)
        _binding = null
        super.onDestroyView()
    }
}