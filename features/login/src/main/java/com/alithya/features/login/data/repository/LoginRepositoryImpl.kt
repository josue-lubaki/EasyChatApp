package com.alithya.features.login.data.repository

import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import com.alithya.features.login.data.repository.datasource.LoginRemoteDataSource
import com.alithya.features.login.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun login(loginForm: LoginForm): LoginStatus {
        return remoteDataSource.login(loginForm = loginForm)
    }
}