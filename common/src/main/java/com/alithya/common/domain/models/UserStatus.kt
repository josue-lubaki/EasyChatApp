package com.alithya.common.domain.models

import com.alithya.common.utils.HttpError

sealed class UserStatus {
    data class Success(val data: InfoUser) : UserStatus()
    data class Error(val httpError: HttpError) : UserStatus()
}
