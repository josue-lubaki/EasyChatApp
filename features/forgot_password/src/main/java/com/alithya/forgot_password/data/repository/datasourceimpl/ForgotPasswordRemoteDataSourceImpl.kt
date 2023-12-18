package com.alithya.forgot_password.data.repository.datasourceimpl

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.data.api.ForgotPasswordService
import com.alithya.forgot_password.data.models.ForgotPasswordRequest
import com.alithya.forgot_password.data.models.ForgotPasswordResetRequest
import com.alithya.forgot_password.data.repository.datasource.ForgotPasswordRemoteDataSource
import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus

internal class ForgotPasswordRemoteDataSourceImpl(
    private val service: ForgotPasswordService
) : ForgotPasswordRemoteDataSource {
    override suspend fun forgotPassword(email: String): PasswordStatus {
        return try {
            val request = ForgotPasswordRequest(email)
            val response = service.forgotPassword(request = request)

            if (response.isSuccessful) {
                PasswordStatus.Success
            } else {
                PasswordStatus.Error(HttpError.handleErrorResponse(response.code()))
            }
        } catch (e: Exception) {
            PasswordStatus.Error(HttpError.handleException(e))
        }
    }

    override suspend fun resetPassword(model: ResetPasswordModel): PasswordStatus {
        try {
            val resetRequest = ForgotPasswordResetRequest(
                model.email,
                model.tempPassword,
                model.newPassword
            )
            val response = service.resetPassword(resetRequest)

            return if (response.isSuccessful) {
                PasswordStatus.Success
            } else {
                PasswordStatus.Error(HttpError.handleErrorResponse(response.code()))
            }
        } catch (e: Exception) {
            return PasswordStatus.Error(HttpError.handleException(e))
        }
    }
}