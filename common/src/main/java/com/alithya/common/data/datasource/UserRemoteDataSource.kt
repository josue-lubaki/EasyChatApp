package com.alithya.common.data.datasource

import com.alithya.common.domain.models.UserStatus
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
    suspend fun getMe() : Flow<UserStatus>
}