package com.alithya.features.login.domain.models

import com.alithya.common.utils.HttpError

sealed class LoginStatus {
    object Success : LoginStatus()
    data class Error(val httpError: HttpError) : LoginStatus()
}