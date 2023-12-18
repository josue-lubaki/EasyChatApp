package com.alithya.features.register.data.repository.datasource

import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User

interface RegisterRemoteDataSource {
    suspend fun register(user: User): RegisterStatus
    suspend fun getAccessToken(): String?
}