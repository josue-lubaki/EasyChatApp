package com.alithya.features.register.di

import com.alithya.features.register.data.remote.api.RegisterService
import com.alithya.features.register.data.repository.RegisterRepositoryImpl
import com.alithya.features.register.data.repository.datasource.RegisterRemoteDataSource
import com.alithya.features.register.data.repository.datasourceimpl.RegisterRemoteDataSourceImpl
import com.alithya.features.register.domain.repository.RegisterRepository
import com.alithya.features.register.domain.usecases.RegisterUseCase
import com.alithya.features.register.presentation.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal fun provideRegisterService(retrofit : Retrofit): RegisterService = retrofit.create(RegisterService::class.java)

val registerModule = module {

    factory { provideRegisterService(retrofit = get()) }

    factory<RegisterRemoteDataSource> {
        RegisterRemoteDataSourceImpl(
            service = get(),
            authenticationService = get()
        )
    }

    factory<RegisterRepository> { RegisterRepositoryImpl(remoteDataSource = get()) }

    factory { RegisterUseCase(repository = get()) }

    viewModel {
        RegisterViewModel(
            useCase = get(),
            dispatcher = get()
        )
    }
}