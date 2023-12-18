package com.alithya.forgot_password.domain.usecases

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.ResetPasswordModel
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository
import java.net.UnknownHostException

class ResetPasswordUseCase (
    private val repository: ForgotPasswordRepository
) {
    suspend operator fun invoke(model : ResetPasswordModel) : PasswordStatus {
        return try {
            repository.resetPassword(model = model)
        } catch (e: Exception) {
            PasswordStatus.Error(
                when(e){
                    is UnknownHostException -> HttpError.INTERNET_CONNECTION
                    else -> HttpError.UNKNOWN
                }
            )
        }
    }
}