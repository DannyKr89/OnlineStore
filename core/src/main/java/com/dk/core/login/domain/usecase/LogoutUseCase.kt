package com.dk.core.login.domain.usecase

import com.dk.core.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val loginRepository: LoginRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return loginRepository.logout()
    }
}