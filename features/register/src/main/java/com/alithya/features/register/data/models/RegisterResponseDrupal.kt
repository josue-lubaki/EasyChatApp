package com.alithya.features.register.data.models


import com.google.gson.annotations.SerializedName

internal data class Access(
    @SerializedName("format")
    val format: String,
    @SerializedName("value")
    val value: String
)

internal data class Changed(
    @SerializedName("format")
    val format: String,
    @SerializedName("value")
    val value: String
)

internal data class Created(
    @SerializedName("format")
    val format: String,
    @SerializedName("value")
    val value: String
)

internal data class DefaultLangcode(
    @SerializedName("value")
    val value: Boolean
)

internal data class FieldFirstname(
    @SerializedName("value")
    val value: String
)

internal data class FieldJob(
    @SerializedName("target_id")
    val targetId: Int,
    @SerializedName("target_type")
    val targetType: String,
    @SerializedName("target_uuid")
    val targetUuid: String,
    @SerializedName("url")
    val url: String
)

internal data class FieldLastname(
    @SerializedName("value")
    val value: String
)

internal data class Langcode(
    @SerializedName("value")
    val value: String
)

internal data class Login(
    @SerializedName("format")
    val format: String,
    @SerializedName("value")
    val value: String
)

internal data class Mail(
    @SerializedName("value")
    val value: String
)

internal data class Name(
    @SerializedName("value")
    val value: String
)

internal data class Path(
    @SerializedName("alias")
    val alias: Any,
    @SerializedName("langcode")
    val langcode: String,
    @SerializedName("pid")
    val pid: Any
)

internal data class PreferredLangcode(
    @SerializedName("value")
    val value: String
)

internal data class Status(
    @SerializedName("value")
    val value: Boolean
)

internal data class Timezone(
    @SerializedName("value")
    val value: String
)

internal data class Uid(
    @SerializedName("value")
    val value: Int
)

internal data class Uuid(
    @SerializedName("value")
    val value: String
)