package com.alithya.forgot_password.domain.models

import com.alithya.common.utils.HttpError

sealed class PasswordStatus {
    object Success : PasswordStatus()
    data class Error(val httpError: HttpError) : PasswordStatus()
}