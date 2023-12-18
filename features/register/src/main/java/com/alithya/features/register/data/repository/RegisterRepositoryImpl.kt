package com.alithya.features.register.data.repository

import com.alithya.features.register.data.repository.datasource.RegisterRemoteDataSource
import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import com.alithya.features.register.domain.repository.RegisterRepository

class RegisterRepositoryImpl(
    private val remoteDataSource : RegisterRemoteDataSource
) : RegisterRepository {

    override suspend fun register(user: User): RegisterStatus {
        return remoteDataSource.register(user = user)
    }
}