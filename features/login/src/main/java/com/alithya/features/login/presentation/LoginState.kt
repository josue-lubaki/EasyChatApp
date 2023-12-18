package com.alithya.features.login.presentation

import androidx.annotation.StringRes

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(@StringRes val message: Int) : LoginState()
}

