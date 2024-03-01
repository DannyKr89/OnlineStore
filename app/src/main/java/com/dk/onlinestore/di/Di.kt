package com.dk.onlinestore.di

import android.content.Context
import android.content.SharedPreferences
import com.dk.api.retrofit.ProductApi
import com.dk.api.retrofit.data.CatalogRepositoryImpl
import com.dk.catalog.ui.adapters.CatalogAdapter
import com.dk.catalog.ui.CatalogViewModel
import com.dk.core.app.MainViewModel
import com.dk.core.catalog.domain.CatalogRepository
import com.dk.core.catalog.domain.GetProductsUseCase
import com.dk.core.login.domain.repository.LoginRepository
import com.dk.core.login.domain.usecase.LoginUseCase
import com.dk.core.login.domain.usecase.SaveProfileUseCase
import com.dk.login.data.LoginRepositoryImpl
import com.dk.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<LoginRepository> { LoginRepositoryImpl(sharedPreferences = get()) }
    factory<LoginUseCase> { LoginUseCase(loginRepository = get()) }
    viewModel<MainViewModel> { MainViewModel(loginUseCase = get()) }
}

val loginModule = module {
    factory<SharedPreferences> {
        get<Context>().getSharedPreferences(
            "Profile", Context.MODE_PRIVATE
        )
    }
    factory<SaveProfileUseCase> { SaveProfileUseCase(loginRepository = get()) }
    viewModel<LoginViewModel> { LoginViewModel(saveProfileUseCase = get()) }
}

val catalogModule = module {
    factory<CatalogAdapter> { CatalogAdapter() }
    single<CatalogRepository> { CatalogRepositoryImpl(api = get()) }
    factory<GetProductsUseCase> { GetProductsUseCase(catalogRepository = get()) }
    viewModel<CatalogViewModel> { CatalogViewModel(getProductsUseCase = get()) }
}

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }
    single<ProductApi> { get<Retrofit>().create(ProductApi::class.java) }
}