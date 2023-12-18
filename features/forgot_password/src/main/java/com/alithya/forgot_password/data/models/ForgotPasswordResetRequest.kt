package com.alithya.forgot_password.data.models

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResetRequest(
    @SerializedName("name") val email: String,
    @SerializedName("temp_pass") val tempPassword: String,
    @SerializedName("new_pass") val newPassword: String,
)
