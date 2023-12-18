package com.alithya.common.data.repository.repositoryimpl

import com.alithya.common.data.datasource.UserRemoteDataSource
import com.alithya.common.domain.models.UserStatus
import com.alithya.common.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getMe() : Flow<UserStatus> = remoteDataSource.getMe()
}