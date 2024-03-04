package com.dk.favorite.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dk.favorite.ui.fragments.BrandsFragment
import com.dk.favorite.ui.fragments.ProductsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = arrayOf(ProductsFragment(), BrandsFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}