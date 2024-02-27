package com.dk.login.data

import android.content.SharedPreferences
import com.dk.core.login.domain.model.Profile
import com.dk.core.login.domain.repository.LoginRepository
import com.dk.login.utils.PROFILE_FIRST_NAME
import com.dk.login.utils.PROFILE_PHONE_NUMBER
import com.dk.login.utils.PROFILE_SECOND_NAME
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(private val sharedPreferences: SharedPreferences) : LoginRepository {
    override suspend fun saveProfile(profile: Profile) {
        sharedPreferences.edit().apply {
            putString(PROFILE_FIRST_NAME, profile.firstName)
            putString(PROFILE_SECOND_NAME, profile.secondName)
            putString(PROFILE_PHONE_NUMBER, profile.phoneNumber)
        }.apply()
    }

    override suspend fun login() = flow {
        val firstName = sharedPreferences.getString(PROFILE_FIRST_NAME, null)
        val secondName = sharedPreferences.getString(PROFILE_SECOND_NAME, null)
        val phoneNumber = sharedPreferences.getString(PROFILE_PHONE_NUMBER, null)
        if (firstName != null && secondName != null && phoneNumber != null) {
            emit(Profile(firstName, secondName, phoneNumber))
        } else {
            emit(null)
        }
    }

    override suspend fun logout() = flow {
        try {
            sharedPreferences.edit().apply {
                remove(PROFILE_FIRST_NAME)
                remove(PROFILE_SECOND_NAME)
                remove(PROFILE_PHONE_NUMBER)
            }.apply()
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }
}