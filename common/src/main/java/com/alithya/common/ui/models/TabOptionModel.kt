package com.alithya.common.ui.models

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector
import com.alithya.common.R

sealed class TabOptionModel(
    @StringRes val title : Int,
    val icon : ImageVector,
    val testTag : String,
) {
    object Courses : TabOptionModel(
        title = R.string.courses,
        icon = Icons.Default.School,
        testTag = "courses_option",
    )

    object People : TabOptionModel(
        title = R.string.people,
        icon = Icons.Default.ContactPage,
        testTag = "people_option",
    )
}
