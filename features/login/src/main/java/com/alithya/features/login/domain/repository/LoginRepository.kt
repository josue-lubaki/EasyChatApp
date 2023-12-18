package com.alithya.features.login.domain.repository

import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm

interface LoginRepository {
    suspend fun login(loginForm: LoginForm) : LoginStatus
}