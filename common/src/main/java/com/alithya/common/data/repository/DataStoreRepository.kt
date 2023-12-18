package com.alithya.common.data.repository

import com.alithya.common.domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow

class DataStoreRepository(
    private val dataStore : DataStoreOperations
) {

    suspend fun onSavePreferredLanguage(language: String) = dataStore.savePreferredLanguage(language)

    suspend fun onReadPreferredLanguage() : Flow<String> = dataStore.readPreferredLanguage()

    suspend fun onSaveAccessToken(token: String) = dataStore.saveAccessToken(token)

    fun onReadAccessToken() : String  = dataStore.readAccessToken()

    suspend fun onSaveCurrentUserID(idUser: String) = dataStore.saveCurrentUserID(idUser)

    suspend fun onReadCurrentUserID() : Flow<String> = dataStore.readCurrentUserID()
}