package com.alithya.common.domain.usecases.read_current_user_id

import com.alithya.common.data.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadCurrentUserIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() : Flow<String> {
        return try {
            dataStoreRepository.onReadCurrentUserID()
        } catch (e: Exception) {
            flowOf(Exception(e).message.toString())
        }
    }
}