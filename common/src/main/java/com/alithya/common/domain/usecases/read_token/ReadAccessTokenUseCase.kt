package com.alithya.common.domain.usecases.read_token

import com.alithya.common.data.repository.DataStoreRepository


class ReadAccessTokenUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() : String {
        return try {
            dataStoreRepository.onReadAccessToken()
        } catch (e: Exception) {
            Exception(e).message.toString()
        }
    }
}