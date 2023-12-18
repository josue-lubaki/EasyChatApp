package com.alithya.common.di

import android.content.Context
import com.alithya.common.data.repository.DataStoreRepository
import com.alithya.common.data.datasource.datasourceimpl.DataStoreOperationsImpl
import com.alithya.common.domain.repository.DataStoreOperations
import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.common.domain.usecases.read_current_user_id.ReadCurrentUserIdUseCase
import com.alithya.common.domain.usecases.read_preferred_language.ReadPreferredLanguageUseCase
import com.alithya.common.domain.usecases.read_token.ReadAccessTokenUseCase
import com.alithya.common.domain.usecases.save_current_user_id.SaveCurrentUserIdUseCase
import com.alithya.common.domain.usecases.save_preferred_language.SavePreferredLanguageUseCase
import com.alithya.common.domain.usecases.save_token.SaveAccessTokenUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {

    factory<DataStoreOperations> {
        DataStoreOperationsImpl(context = get())
    }

    factory<DataStoreRepository> {
        provideRepository(dataStore = get())
    }

    factory<UseCasesCommon> {
        UseCasesCommon (
            savePreferredLanguageUseCase = SavePreferredLanguageUseCase(dataStoreRepository = get()),
            readPreferredLanguageUseCase = ReadPreferredLanguageUseCase(dataStoreRepository = get()),
            saveAccessTokenUseCase = SaveAccessTokenUseCase(dataStoreRepository = get()),
            readAccessTokenUseCase = ReadAccessTokenUseCase(dataStoreRepository = get()),
            readCurrentUserIdUseCase = ReadCurrentUserIdUseCase(dataStoreRepository = get()),
            saveCurrentUserIdUseCase = SaveCurrentUserIdUseCase(dataStoreRepository = get())
        )
    }
}