package com.alithya.common.domain.repository

import com.alithya.common.domain.models.UserStatus
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getMe() : Flow<UserStatus>
}