package com.alithya.profile.presentation

import androidx.annotation.StringRes
import com.alithya.common.domain.models.InfoUser
import com.alithya.common.domain.models.States

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ProfileState : States {
    object Idle : ProfileState()
    object Loading : ProfileState()
    data class Success(val user: InfoUser) : ProfileState()
    data class Error(@StringRes val message: Int) : ProfileState()
}