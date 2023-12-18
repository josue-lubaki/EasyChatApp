package com.alithya.forgot_password.domain.repository

import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus

interface ForgotPasswordRepository {
    suspend fun forgotPassword(email : String): PasswordStatus
    suspend fun resetPassword(model : ResetPasswordModel): PasswordStatus
}