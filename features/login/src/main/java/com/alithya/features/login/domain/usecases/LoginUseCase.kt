package com.alithya.features.login.domain.usecases

import com.alithya.features.login.domain.models.LoginStatus
import com.alithya.features.login.domain.models.LoginForm
import com.alithya.features.login.domain.repository.LoginRepository

class LoginUseCase(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(loginForm : LoginForm) : LoginStatus = repository.login(loginForm = loginForm)
}