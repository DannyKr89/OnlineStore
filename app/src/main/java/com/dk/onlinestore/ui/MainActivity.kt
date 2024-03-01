package com.dk.onlinestore.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dk.core.app.MainViewModel
import com.dk.onlinestore.R
import com.dk.onlinestore.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupToolbar()
        setupNavigation()
    }

    private fun initViewModel() {
        viewModel.login()
        viewModel.loginLiveData.observe(this) {
            if (it != null) {
                navController.navigate(R.id.catalog_fragment)
            } else {
                navController.navigate(R.id.login_fragment)
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.login_fragment,
                R.id.main_fragment,
                R.id.catalog_fragment,
                R.id.basket_fragment,
                R.id.discount_fragment,
                R.id.account_fragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            with(binding) {
                navView.isVisible = destination.id != R.id.login_fragment
            }
            supportActionBar?.title = destination.label
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.isTitleCentered = true
    }
}