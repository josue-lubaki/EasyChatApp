package com.alithya.features.register.presentation

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

sealed class RegisterEvent {
    data class OnRegister(
        val firstname : String,
        val lastname : String,
        val email : String,
        val password : String
    ) : RegisterEvent()
}

