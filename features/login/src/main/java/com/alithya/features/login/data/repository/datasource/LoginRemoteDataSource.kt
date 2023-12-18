package com.alithya.features.login.data.repository.datasource

import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm

interface LoginRemoteDataSource {
    suspend fun login(loginForm: LoginForm) : LoginStatus
}