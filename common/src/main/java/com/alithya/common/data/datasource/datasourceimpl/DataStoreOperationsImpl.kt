package com.alithya.common.data.datasource.datasourceimpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alithya.common.domain.repository.DataStoreOperations
import com.alithya.common.utils.Constants.ACCESS_TOKEN_KEY
import com.alithya.common.utils.Constants.ID_USER
import com.alithya.common.utils.Constants.PREFERENCES_NAME
import com.alithya.common.utils.Constants.PREFERRED_LANGUAGE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.util.Locale

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private val dataStore : DataStore<Preferences> = context.dataStore

    private object PreferencesKeys {
        val preferredLanguageKey : Preferences.Key<String> = stringPreferencesKey(name = PREFERRED_LANGUAGE_KEY)
        val accessTokenKey : Preferences.Key<String> = stringPreferencesKey(name = ACCESS_TOKEN_KEY)
        val idUserCurrentKey : Preferences.Key<String> = stringPreferencesKey(name = ID_USER)
    }

    override suspend fun savePreferredLanguage(language: String) {
        dataStore.edit { preferencesArray ->
            preferencesArray[PreferencesKeys.preferredLanguageKey] = language
        }
    }

    override suspend fun readPreferredLanguage(): Flow<String> {
        return dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferencesArray ->
            preferencesArray[PreferencesKeys.preferredLanguageKey] ?: Locale.ENGLISH.language
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferencesArray ->
            preferencesArray[PreferencesKeys.accessTokenKey] = token
        }
    }

    override fun readAccessToken(): String {
        return runBlocking {
            dataStore.data.catch { exception ->
                if(exception is IOException){
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferencesArray ->
                preferencesArray[PreferencesKeys.accessTokenKey] ?: ""
            }.first()
        }
    }

    override suspend fun saveCurrentUserID(idUser: String) {
        dataStore.edit { preferencesArray ->
            preferencesArray[PreferencesKeys.idUserCurrentKey] = idUser
        }
    }

    override suspend fun readCurrentUserID(): Flow<String> {
        return dataStore.data.catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferencesArray ->
            preferencesArray[PreferencesKeys.idUserCurrentKey] ?: ""
        }
    }

}