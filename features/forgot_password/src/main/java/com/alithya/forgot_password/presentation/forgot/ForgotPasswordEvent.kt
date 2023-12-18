package com.alithya.forgot_password.presentation.forgot

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ForgotPasswordEvent {
    data class OnForgotPassword(val email : String) : ForgotPasswordEvent()
}