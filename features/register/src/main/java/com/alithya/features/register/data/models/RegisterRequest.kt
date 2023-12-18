package com.alithya.features.register.data.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @SerializedName("name") val name: List<String>,
    @SerializedName("field_firstname") val firstname: List<String>,
    @SerializedName("field_lastname") val lastname: List<String>,
    @SerializedName("mail") val email: List<String>,
    @SerializedName("pass") val password: List<String>,
)