package com.alithya.forgot_password.data.models

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("mail") val email: String
)
