//package ca.josue_lubaki.chat.presentation
//
///**
// * created by Josue Lubaki
// * date : 2023-12-18
// * version : 1.0.0
// */
//
//sealed class ChatState {
//    object Idle : ChatState()
//    object Loading : ChatState()
//    data class Success(val data: Chat) : ChatState()
//    data class Error(val error: Exception) : ChatState()
//}