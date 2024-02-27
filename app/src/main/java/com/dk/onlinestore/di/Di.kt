package com.dk.onlinestore.di

import android.content.Context
import android.content.SharedPreferences
import com.dk.login.data.LoginRepositoryImpl
import com.dk.core.login.domain.repository.LoginRepository
import com.dk.core.login.domain.usecase.LoginUseCase
import com.dk.core.login.domain.usecase.SaveProfileUseCase
import com.dk.login.ui.LoginViewModel
import com.dk.core.app.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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