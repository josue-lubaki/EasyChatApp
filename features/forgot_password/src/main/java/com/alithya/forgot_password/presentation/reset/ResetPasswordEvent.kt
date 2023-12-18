package com.alithya.forgot_password.presentation.reset


/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ResetPasswordEvent {
    data class OnResetPassword(
        val email: String,
        val tempPassword: String,
        val newPassword: String
    ) : ResetPasswordEvent()
}