package ca.josue_lubaki.chat.presentation

/**
 * created by Josue Lubaki
 * date : 2023-12-18
 * version : 1.0.0
 */

sealed class ChatEvent {
    object OnLoadData : ChatEvent()
}