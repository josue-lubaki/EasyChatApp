package ca.josue_lubaki.loginatp.presentation

/**
 * created by Josue Lubaki
 * date : 2023-12-18
 * version : 1.0.0
 */

sealed class LoginATPEvent {
    object OnLoadData : LoginATPEvent()
}