package com.alithya.features.register.domain.usecases

import com.alithya.features.register.domain.models.RegisterStatus
import com.alithya.features.register.domain.models.User
import com.alithya.features.register.domain.repository.RegisterRepository

class RegisterUseCase(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(user: User) : RegisterStatus = repository.register(user = user)
}