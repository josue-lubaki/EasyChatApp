package com.alithya.common.domain.usecases.save_current_user_id

import com.alithya.common.data.repository.DataStoreRepository


class SaveCurrentUserIdUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(idUser: String) {
        return try {
            dataStoreRepository.onSaveCurrentUserID(idUser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}