package com.alithya.forgot_password.data.repository

import com.alithya.forgot_password.data.repository.datasource.ForgotPasswordRemoteDataSource
import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository

class ForgotPasswordRepositoryImpl(
    private val remoteDataSource: ForgotPasswordRemoteDataSource
) : ForgotPasswordRepository {
    override suspend fun forgotPassword(email: String): PasswordStatus {
        return remoteDataSource.forgotPassword(email)
    }

    override suspend fun resetPassword(model: ResetPasswordModel): PasswordStatus {
        return remoteDataSource.resetPassword(model)
    }
}