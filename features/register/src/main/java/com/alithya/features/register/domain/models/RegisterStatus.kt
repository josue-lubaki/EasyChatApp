package com.alithya.features.register.domain.models

import com.alithya.common.utils.HttpError

sealed class RegisterStatus {
    object Success : RegisterStatus()
    data class Error(val httpError : HttpError) : RegisterStatus()
}
