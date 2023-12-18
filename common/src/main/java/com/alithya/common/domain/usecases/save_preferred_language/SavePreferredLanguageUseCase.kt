package com.alithya.common.domain.usecases.save_preferred_language

import com.alithya.common.data.repository.DataStoreRepository


class SavePreferredLanguageUseCase(
    private val dataStoreRepository : DataStoreRepository
) {
    suspend operator fun invoke(language : String) = dataStoreRepository.onSavePreferredLanguage(language)
}