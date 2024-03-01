package com.dk.onlinestore.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.dk.core.app.MainViewModel
import com.dk.core.app.utils.PROFILE_FIRST_NAME
import com.dk.onlinestore.R
import com.dk.onlinestore.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModel()
    private val sharedPreferences: SharedPreferences = get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupToolbar()
        setupNavigation()
        checkProfile()
    }

    private fun checkProfile() {
        val profile = sharedPreferences.getString(PROFILE_FIRST_NAME, null)
        if (profile != null) {
            navController.navigate(R.id.catalog_fragment)
        }
    }

    private fun initViewModel() {
        viewModel.productLiveData.observe(this) {
            if (it != null) {
                navController.navigate(R.id.action_catalog_fragment_to_product_detail_fragment)
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

    override fun onSupportNavigateUp(): Boolean {
        viewModel.product(null)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}