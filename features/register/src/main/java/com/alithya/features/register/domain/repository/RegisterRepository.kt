package com.alithya.features.register.domain.repository

import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User

interface RegisterRepository {
    suspend fun register(user : User) : RegisterStatus
}