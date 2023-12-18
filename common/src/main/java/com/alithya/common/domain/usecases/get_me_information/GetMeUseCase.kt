package com.alithya.common.domain.usecases.get_me_information

import com.alithya.common.domain.models.UserStatus
import com.alithya.common.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetMeUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke() : Flow<UserStatus> = repository.getMe()
}