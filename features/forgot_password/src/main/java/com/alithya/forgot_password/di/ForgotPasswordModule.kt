package com.alithya.forgot_password.di

import com.alithya.forgot_password.data.api.ForgotPasswordService
import com.alithya.forgot_password.data.repository.ForgotPasswordRepositoryImpl
import com.alithya.forgot_password.data.repository.datasource.ForgotPasswordRemoteDataSource
import com.alithya.forgot_password.data.repository.datasourceimpl.ForgotPasswordRemoteDataSourceImpl
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository
import com.alithya.forgot_password.domain.usecases.ResetPasswordUseCase
import com.alithya.forgot_password.domain.usecases.ForgotPasswordUseCase
import com.alithya.forgot_password.domain.usecases.PasswordUseCases
import com.alithya.forgot_password.presentation.forgot.ForgotPasswordViewModel
import com.alithya.forgot_password.presentation.reset.ResetPasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

internal fun provideForgotPasswordService(retrofit : Retrofit): ForgotPasswordService = retrofit.create(
    ForgotPasswordService::class.java)

val forgotPasswordModule = module {

    factory { provideForgotPasswordService(retrofit = get()) }
    factory<ForgotPasswordRemoteDataSource> { ForgotPasswordRemoteDataSourceImpl(service = get()) }
    factory<ForgotPasswordRepository> { ForgotPasswordRepositoryImpl(remoteDataSource = get()) }
    factory { ForgotPasswordUseCase(repository = get()) }
    factory { ResetPasswordUseCase(repository = get()) }
    factory {
        PasswordUseCases(
            forgotPasswordUseCase = get(),
            resetPasswordUseCase = get()
        )
    }

    viewModel {
        ForgotPasswordViewModel(
            useCases = get(),
            dispatcher = get()
        )
    }

    viewModel {
        ResetPasswordViewModel(
            useCases = get(),
            dispatcher = get()
        )
    }
}