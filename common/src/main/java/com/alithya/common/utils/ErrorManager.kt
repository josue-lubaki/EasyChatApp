package com.alithya.common.utils

import com.alithya.common.R
import java.lang.Exception
import java.net.UnknownHostException

/**
 * created by Josue Lubaki
 * date : 2023-03-31
 * version : 1.0.0
 */

sealed class HttpError {
    object UNAUTHORIZED : HttpError()
    object INTERNET_CONNECTION : HttpError()
    object INTERNAL_SERVER_ERROR : HttpError()
    object SERVER_UNAVAILABLE : HttpError()
    object BAD_REQUEST : HttpError()
    object CSRF_TOKEN_MISSING : HttpError()
    object ACCESS_TOKEN_MISSING : HttpError()
    object INVALID_PASSWORD : HttpError()
    object UNKNOWN : HttpError()

    fun getErrorMessage(): Int {
        return when (this) {
            UNAUTHORIZED -> R.string.error_unauthorized
            BAD_REQUEST -> R.string.login_bad_request_msg
            INTERNET_CONNECTION -> R.string.error_internet_connection
            SERVER_UNAVAILABLE -> R.string.error_server_connection
            BAD_REQUEST -> R.string.bad_request_msg
            CSRF_TOKEN_MISSING -> R.string.error_server_connection
            ACCESS_TOKEN_MISSING -> R.string.error_access_token_missing
            INVALID_PASSWORD -> R.string.invalid_password
            else -> R.string.error_unknown
        }
    }

    companion object {
        fun handleException(exception: Exception) : HttpError {
            return when (exception) {
                is UnknownHostException -> INTERNET_CONNECTION
                else -> UNKNOWN
            }
        }

        fun handleErrorResponse(code: Int) : HttpError {
            return when (code) {
                401 -> UNAUTHORIZED
                400 -> BAD_REQUEST
                422 -> INVALID_PASSWORD
                500 -> INTERNAL_SERVER_ERROR
                else -> UNKNOWN
            }
        }
    }
}