package com.alithya.features.register.presentation

import androidx.annotation.StringRes

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(@StringRes val message: Int) : RegisterState()
}

