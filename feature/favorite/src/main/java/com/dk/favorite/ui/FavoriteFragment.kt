package com.dk.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dk.favorite.R
import com.dk.favorite.databinding.FragmentFavoriteBinding
import com.dk.favorite.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        with(binding) {
            vpFavorite.adapter = ViewPagerAdapter(this@FavoriteFragment)
            TabLayoutMediator(tlFavorite, vpFavorite) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.products)
                    1 -> getString(R.string.brands)
                    else -> null
                }
            }.attach()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}