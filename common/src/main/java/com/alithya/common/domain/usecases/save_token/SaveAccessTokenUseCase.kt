package com.alithya.common.domain.usecases.save_token

import com.alithya.common.data.repository.DataStoreRepository

class SaveAccessTokenUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String) = dataStoreRepository.onSaveAccessToken(token)
}