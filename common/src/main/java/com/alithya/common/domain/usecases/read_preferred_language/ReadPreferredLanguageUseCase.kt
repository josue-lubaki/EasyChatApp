package com.alithya.common.domain.usecases.read_preferred_language

import com.alithya.common.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadPreferredLanguageUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() : Flow<String> {
        return try {
            dataStoreRepository.onReadPreferredLanguage()
        } catch (e: Exception) {
            flowOf(Exception(e).message.toString())
        }
    }
}