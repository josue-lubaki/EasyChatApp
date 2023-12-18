package com.alithya.common.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import kotlin.math.abs

object Constants {
    const val NETWORK_TIME_OUT = 10L
    const val REGEX_ALITHYA_MAIL = "^[A-Za-z0-9-.]+@alithya.com$"
    const val USECASE_REGEX = "[A-Za-z]+UseCase?(Test)?"
    const val USECASES_REGEX = "[A-Za-z]+UseCases?(Test)?"

    const val EMAIL_ARGUMENT_KEY = "email"
    const val UUID_ARGUMENT_KEY = "uuid"

    const val PREFIX_IMAGE_URL = "/sites"

    const val PREFERENCES_NAME = "sequoia_preferences"
    const val PREFERRED_LANGUAGE_KEY = "preferred_language"
    const val ACCESS_TOKEN_KEY = "access_token"
    const val ID_USER = "id_user"

    const val FADE_DURATION_DEFAULT = 300
    const val FADE_DURATION_LONG = 500
    const val EXPAND_DURATION = 300

    // headers
    const val AUTHORIZATION = "Authorization"
    const val ACCEPT = "Accept"
    const val X_CSRF_TOKEN = "X-CSRF-Token"
    const val CONTENT_TYPE = "Content-Type"
    const val CONTENT_DISPOSITION = "Content-Disposition"
    const val CONTENT_LENGTH = "Content-Length"

    // values for the header
    const val BEARER = "Bearer"
    const val APPLICATION_JSON = "application/json"
    const val TEXT_PLAIN = "text/plain"
    const val APPLICATION_OCTET_STREAM = "application/octet-stream"
    const val MULTIPART_FORM_DATA = "multipart/form-data"
    const val APPLICATION_VND_API_JSON = "application/vnd.api+json"
    const val FILE = "file"
    const val CONTENT_DISPOSITION_VALUE = "\"file\"; filename=\"123.jpg\""

    fun getLocaleDateFromTimestamp(timestamp: Long): String? {
        val date = Date(timestamp * 1000)
        val formatter = SimpleDateFormat.getDateInstance()
        return formatter.format(date)
    }

}