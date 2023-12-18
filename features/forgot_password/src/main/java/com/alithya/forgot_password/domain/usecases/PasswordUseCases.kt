package com.alithya.forgot_password.domain.usecases

import com.alithya.forgot_password.domain.models.ResetPasswordModel

class PasswordUseCases(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) {
    suspend fun forgotPassword(email: String) = forgotPasswordUseCase(email)
    suspend fun resetPassword(model: ResetPasswordModel) = resetPasswordUseCase(model)
}