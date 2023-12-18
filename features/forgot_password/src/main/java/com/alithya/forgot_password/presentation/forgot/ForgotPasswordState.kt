package com.alithya.forgot_password.presentation.forgot

import androidx.annotation.StringRes

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ForgotPasswordState {
    object Idle : ForgotPasswordState()
    object Loading : ForgotPasswordState()
    object Success : ForgotPasswordState()
    data class Error(@StringRes val error: Int) : ForgotPasswordState()
}