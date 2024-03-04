package com.dk.core.login.domain.repository

import com.dk.core.login.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun saveProfile(profile: Profile)
    suspend fun login(): Flow<Profile?>
    suspend fun logout(): Flow<Boolean>
}