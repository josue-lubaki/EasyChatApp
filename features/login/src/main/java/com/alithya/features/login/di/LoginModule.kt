package com.alithya.features.login.di

import com.alithya.common.domain.usecases.UseCasesCommon
import com.alithya.features.login.data.api.LoginService
import com.alithya.features.login.data.repository.LoginRepositoryImpl
import com.alithya.features.login.data.repository.datasource.LoginRemoteDataSource
import com.alithya.features.login.data.repository.datasourceimpl.LoginRemoteDataSourceImpl
import com.alithya.features.login.domain.repository.LoginRepository
import com.alithya.features.login.domain.usecases.LoginUseCase
import com.alithya.features.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal fun provideLoginService(retrofit : Retrofit): LoginService = retrofit.create(LoginService::class.java)

val loginModule = module {

    factory { provideLoginService(retrofit = get()) }

    factory<LoginRemoteDataSource> {
        LoginRemoteDataSourceImpl(
            service = get(),
            useCasesCommon = get()
        )
    }

    factory<LoginRepository> { LoginRepositoryImpl(remoteDataSource = get()) }

    factory<LoginUseCase> { LoginUseCase(repository = get()) }

    viewModel {
        LoginViewModel(
            useCase = get(),
            dispatcher = get()
        )
    }
}