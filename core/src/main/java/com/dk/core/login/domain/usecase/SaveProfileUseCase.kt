package com.dk.core.login.domain.usecase

import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.repository.LoginRepository

class SaveProfileUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(profile: Profile) {
        loginRepository.saveProfile(profile)
    }
}