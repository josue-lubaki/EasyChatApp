package com.alithya.profile.presentation

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

sealed class ProfileEvent {
    object OnLoadMyProfile : ProfileEvent()
}