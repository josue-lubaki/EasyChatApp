package com.alithya.common.utils

/**
 * created by Josue Lubaki
 * date : 2023-04-11
 * version : 1.0.0
 */

object Endpoints {
    const val OAUTH_TOKEN = "/oauth/token"
    const val REGISTER = "/entity/user"
    const val SESSION_TOKEN = "/session/token"
    const val API_ME = "/api/me"
    const val UPDATE_PROFILE_PICTURE = "/api/user/user/{userId}/field_profile_image"
    const val CHANGE_PASSWORD = "/api/user/user/{id}"
    const val GRAPHQL = "graphql"
    const val LOST_PASSWORD = "/user/lost-password/?_format=json"
    const val LOST_PASSWORD_RESET = "/user/lost-password-reset/?_format=json"
    const val EN_TAXONOMY_PROFESSION = "/en/api/taxonomy/terms/profession"
    const val FR_TAXONOMY_PROFESSION = "/fr/api/taxonomy/terms/profession"
}