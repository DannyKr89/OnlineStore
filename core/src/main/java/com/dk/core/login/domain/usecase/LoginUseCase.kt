package com.dk.core.login.domain.usecase

import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(): Flow<Profile?> {
        return loginRepository.login()
    }
}