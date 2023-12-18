package com.alithya.common.ui.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.alithya.common.R
import com.alithya.common.navigation.ScreenTarget

sealed class ProfileCellModel(
    @StringRes val text : Int,
    val icon : ImageVector,
    val testTag : String,
    val screen : ScreenTarget
) {
    object AccountInformation: ProfileCellModel(
        text = R.string.account_information,
        icon = Icons.Filled.Person,
        testTag = "account_information",
        screen = ScreenTarget.AccountInformation
    )

    object WorkSkills: ProfileCellModel(
        text = R.string.work_skills,
        icon = Icons.Filled.AutoFixHigh,
        testTag = "work_skills",
        screen = ScreenTarget.WorkSkills
    )

    object Settings: ProfileCellModel(
        text = R.string.settings,
        icon = Icons.Filled.Settings,
        testTag = "settings",
        screen = ScreenTarget.Settings
    )

    object ChangePassword: ProfileCellModel(
        text = R.string.change_password,
        icon = Icons.Filled.Edit,
        testTag = "change_password",
        screen = ScreenTarget.ChangePassword
    )

    object ChangeProfession: ProfileCellModel(
        text = R.string.change_profession,
        icon = Icons.Filled.AutoAwesome,
        testTag = "change_profession",
        screen = ScreenTarget.ChangeProfession
    )
}
