package com.dk.favorite.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dk.core.app.ui.MainViewModel
import com.dk.core.catalog.ui.adapters.CatalogAdapter
import com.dk.favorite.databinding.FragmentProductsBinding
import com.dk.favorite.ui.FavoriteViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding get() = _binding!!
    private val adapter: CatalogAdapter = get()
    private val viewModel: FavoriteViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
        setupAdapter()
    }

    private fun setupAdapter() {
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

    private fun initViews() {
        with(binding) {
            rvFavoriteProducts.adapter = adapter
        }
    }

    private fun initViewModel() {
        viewModel.getFavoriteProducts()
        mainViewModel.removeLiveData.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getFavoriteProducts()
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}