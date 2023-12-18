package com.alithya.common.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.themes.EasyChatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderNav(
    title: String,
    icon: @Composable () -> Unit = {
        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = null)
    },
    visibleBottomDivider: Boolean = true,
    isVisible: Boolean = true,
    onClick: () -> Unit,
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                AnimatedVisibility (
                    visible = isVisible,
                    enter = fadeIn() + expandIn(),
                    exit = fadeOut() + shrinkOut(),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { onClick() }) {
                    icon()
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                titleContentColor = MaterialTheme.colorScheme.background,
                actionIconContentColor = MaterialTheme.colorScheme.background,
                navigationIconContentColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
            )
        )

        if (visibleBottomDivider) {
            Divider(
                color = MaterialTheme.colorScheme.secondaryContainer,
                thickness = 1.dp
            )
        }
    }
}

@Preview
@Composable
private fun HeaderNavPreview() {
    EasyChatTheme {
        HeaderNav(
            title = "Work Skils",
            onClick = {}
        )
    }
}