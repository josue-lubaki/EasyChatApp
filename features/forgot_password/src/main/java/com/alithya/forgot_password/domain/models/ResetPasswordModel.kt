package com.alithya.forgot_password.domain.models

data class ResetPasswordModel(
    val email : String,
    val tempPassword : String,
    val newPassword : String,
)
