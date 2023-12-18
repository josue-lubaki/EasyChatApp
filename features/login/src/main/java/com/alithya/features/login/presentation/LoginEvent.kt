package com.alithya.features.login.presentation

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

sealed class LoginEvent {
    data class OnLogin(val email: String, val password : String) : LoginEvent()
}

