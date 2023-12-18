package com.alithya.forgot_password.presentation.reset

import androidx.annotation.StringRes

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ResetPasswordState {
    object Idle : ResetPasswordState()
    object Loading : ResetPasswordState()
    object Success : ResetPasswordState()
    data class Error(@StringRes val error: Int) : ResetPasswordState()
}