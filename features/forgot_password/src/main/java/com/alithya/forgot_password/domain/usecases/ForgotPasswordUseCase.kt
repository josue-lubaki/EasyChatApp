package com.alithya.forgot_password.domain.usecases

import com.alithya.common.utils.HttpError
import com.alithya.forgot_password.domain.models.PasswordStatus
import com.alithya.forgot_password.domain.repository.ForgotPasswordRepository
import java.net.UnknownHostException

class ForgotPasswordUseCase (
    private val repository: ForgotPasswordRepository
) {
    suspend operator fun invoke(email: String): PasswordStatus {
        return try {
            repository.forgotPassword(email)
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