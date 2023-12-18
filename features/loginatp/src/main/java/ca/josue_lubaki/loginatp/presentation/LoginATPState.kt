//package ca.josue_lubaki.loginatp.presentation
//
//import androidx.annotation.StringRes
//
///**
// * created by Josue Lubaki
// * date : 2023-12-18
// * version : 1.0.0
// */
//
//sealed class LoginATPState {
//    object Idle : LoginATPState()
//    object Loading : LoginATPState()
//    data class Success(val data: LoginATP) : LoginATPState()
//    data class Error(val error: Exception) : LoginATPState()
//}