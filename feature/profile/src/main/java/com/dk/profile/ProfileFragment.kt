package com.dk.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.dk.core.app.ui.MainViewModel
import com.dk.profile.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel.getProfile()
        viewModel.getFavoriteProducts()
        viewModel.favoriteLiveData.observe(viewLifecycleOwner) {
            binding.tvFavoriteCount.isVisible = it.isNotEmpty()
            val size = it.size
            binding.tvFavoriteCount.text = StringBuilder(it.size.toString()).append(
                when {
                    size % 10 == 1 && size % 100 != 11 -> getString(com.dk.core.R.string.product)
                    size % 10 in 2..4 && size % 100 !in 12..14 -> getString(com.dk.core.R.string.products)
                    else -> getString(com.dk.core.R.string.many_products)
                }
            )
        }
        viewModel.logoutLiveData.observe(viewLifecycleOwner) {
            mainViewModel.login()
        }
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding) {
                    tvProfileName.text =
                        StringBuilder(it.firstName).append(" ").append(it.secondName)
                    tvProfilePhone.text = it.phoneNumber
                }
            }
        }
    }

    private fun initViews() {
        with(binding) {
            btnExit.setOnClickListener {
                viewModel.logout()
            }
            btnFavorite.setOnClickListener {
                val request = NavDeepLinkRequest.Builder
                    .fromUri(resources.getString(com.dk.core.R.string.to_favorite_fragment).toUri())
                    .build()
                findNavController().navigate(
                    request
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}