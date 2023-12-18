package com.alithya.forgot_password.data.models

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @SerializedName("message") val message: String
)
