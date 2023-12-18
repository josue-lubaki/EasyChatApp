package com.alithya.common.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun savePreferredLanguage(language : String)
    suspend fun readPreferredLanguage() : Flow<String>
    suspend fun saveAccessToken(token: String)
    fun readAccessToken() : String
    suspend fun saveCurrentUserID(idUser: String)
    suspend fun readCurrentUserID() : Flow<String>
}