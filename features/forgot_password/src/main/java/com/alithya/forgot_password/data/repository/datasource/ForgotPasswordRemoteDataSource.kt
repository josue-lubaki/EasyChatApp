package com.alithya.forgot_password.data.repository.datasource

import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus

interface ForgotPasswordRemoteDataSource {
    suspend fun forgotPassword(email : String): PasswordStatus
    suspend fun resetPassword(model : ResetPasswordModel): PasswordStatus
}