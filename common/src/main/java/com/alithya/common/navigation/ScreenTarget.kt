package com.alithya.common.navigation

import com.alithya.common.utils.Constants.EMAIL_ARGUMENT_KEY
import com.alithya.common.utils.Constants.UUID_ARGUMENT_KEY

sealed class ScreenTarget(val route : String) {
    object Splash : ScreenTarget("splash")
    object Login : ScreenTarget("login")
    object LoginATP : ScreenTarget("loginatp")
    object LoginOTP : ScreenTarget("loginotp")
    object LoginUsername : ScreenTarget("loginUsername")
    object Register : ScreenTarget("register")
    object Home : ScreenTarget("home")
    object Search : ScreenTarget("search")
    object Profile : ScreenTarget("profile")
    object ForgotPassword : ScreenTarget("forgot_password")
    object ResetPassword : ScreenTarget("reset_password/{$EMAIL_ARGUMENT_KEY}") {
        fun passArgs(email : String) = "reset_password/$email"
    }
    object App : ScreenTarget("app")
    object AccountInformation : ScreenTarget("account_information")
    object Settings : ScreenTarget("settings")
    object ChangePassword: ScreenTarget("change_password")
    object ChangeProfession: ScreenTarget("change_profession")
    object WorkSkills: ScreenTarget("work_skills")
    object Error : ScreenTarget("error")

    object DetailsFormation : ScreenTarget("details_formation/{$UUID_ARGUMENT_KEY}") {
        fun passArgs(uuidFormation : String) = "details_formation/$uuidFormation"
    }
}
