package com.alithya.common.domain.models

data class InfoUser(
    val id: String,
    val name: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val displayName: String,
    val jobTitle: String?,
    val managerFullName: String?,
    var imageUrl : String?,
)
