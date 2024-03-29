package com.dk.catalog.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.view.forEach
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import com.dk.catalog.R
import com.dk.catalog.databinding.FragmentCatalogBinding
import com.dk.catalog.ui.common.sort.SortList
import com.dk.catalog.ui.common.sort.SortingType
import com.dk.core.app.ui.MainViewModel
import com.dk.core.catalog.domain.model.Product
import com.dk.core.catalog.ui.adapters.CatalogAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.ChipGroup.OnCheckedStateChangeListener
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.set

class CatalogFragment : Fragment(), OnItemSelectedListener, OnCheckedStateChangeListener {

    private var _binding: FragmentCatalogBinding? = null
    private val binding: FragmentCatalogBinding get() = _binding!!
    private val viewModel: CatalogViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModel()
    private val adapter: CatalogAdapter = get()
    private var productList = listOf<Product>()
    private var sortList = SortList(SortingType.RATING_DESC)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter.productListener = {
            mainViewModel.product(it)
        }
        adapter.favoriteListener = {
            when (it.isFavorite) {
                true -> {
                    mainViewModel.removeFromFavorite(it)
                }

                false -> {
                    mainViewModel.addToFavorite(it)
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel.getProducts()
        viewModel.liveData.observe(viewLifecycleOwner) {
            productList = it
            adapter.submitList(sortList.getSorted(productList))
            createChips()
        }
    }

    private fun initViews() {
        with(binding) {
            rvCatalog.adapter = adapter
            spinnerSorting.adapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.sorting, R.layout.item_spinner
            )
            spinnerSorting.onItemSelectedListener = this@CatalogFragment

            chipGroup.setOnCheckedStateChangeListener(this@CatalogFragment)
        }
    }

    private fun createChips() {
        val map = hashMapOf<String, String>()
        productList.forEach { product ->
            product.tags.forEach { tag ->
                map[tag] = when (tag) {
                    getString(com.dk.core.R.string.face) -> resources.getString(com.dk.core.R.string.chip_face)
                    getString(com.dk.core.R.string.body) -> resources.getString(com.dk.core.R.string.chip_body)
                    getString(com.dk.core.R.string.suntan) -> resources.getString(com.dk.core.R.string.chip_suntan)
                    getString(com.dk.core.R.string.mask) -> resources.getString(com.dk.core.R.string.chip_mask)
                    else -> {
                        throw IllegalStateException()
                    }
                }
            }
        }
        if (binding.chipGroup.size > 1) {
            binding.chipGroup.removeViews(1, binding.chipGroup.childCount - 1)
        }
        map.forEach {
            val chip = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_chip, binding.chipGroup, false) as Chip
            chip.apply {
                text = it.value
                transitionName = it.key
            }
            binding.chipGroup.addView(chip)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) = Unit

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> sortList(SortingType.RATING_DESC)
            1 -> sortList(SortingType.PRICE_DESC)
            2 -> sortList(SortingType.PRICE_ASC)
        }
    }

    private fun sortList(sortingType: SortingType) {
        sortList = SortList(sortingType)
        adapter.submitList(sortList.getSorted(adapter.currentList))
    }

    override fun onCheckedChanged(group: ChipGroup, checkedIds: MutableList<Int>) {
        chipCloseButtonVisibility()
        group.forEach { chip ->
            if ((chip as Chip).isChecked) {
                val list = productList.filter { it.tags.contains(chip.transitionName) }
                adapter.submitList(sortList.getSorted(list))
            }
        }
        when (group.checkedChipId) {
            R.id.chip_view_all -> adapter.submitList(sortList.getSorted(productList))
        }
        binding.rvCatalog.postDelayed({
            binding.rvCatalog.scrollToPosition(0)
        }, 10)
    }

    private fun chipCloseButtonVisibility() {
        binding.chipGroup.forEach {
            (it as Chip).isCloseIconVisible = it.isChecked
            it.setOnCloseIconClickListener { binding.chipGroup.check(binding.chipViewAll.id) }
        }
    }

    override fun onDestroyView() {
        binding.rvCatalog.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
