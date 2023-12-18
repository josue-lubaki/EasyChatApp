package com.alithya.common.ui.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Composable
fun NewTag(
    modifier: Modifier = Modifier,
    title: String,
    isRounded: Boolean = false,
    onClick: (String) -> Unit = {},
) {
    val infiniteTransition = rememberInfiniteTransition(label = title)
    val shimmerColor = listOf(
        MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
        MaterialTheme.colorScheme.background.copy(alpha = 0.2f)
    )
    val position by infiniteTransition.animateFloat(
        initialValue = -50f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                delayMillis = 2000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = title
    )
    val gradient = Brush.linearGradient(
        shimmerColor,
        start = Offset(-50f, 0f),
        end = Offset(position, 0f)
    )

    Box(
        modifier = modifier
            .clip(
                if (!isRounded) {
                    MaterialTheme.shapes.medium
                } else {
                    MaterialTheme.shapes.extraLarge
                }
            )
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .background(gradient)
            .clickable(onClick = { onClick(title) })
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimensions.paddingMicro,
                    vertical = MaterialTheme.dimensions.micro
                )
        )
    }
}

@Preview
@Composable
private fun NewTagPreview() {
    EasyChatTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimensions.micro),
            contentAlignment = Alignment.Center,
        ) {
            NewTag(title = "New")
        }
    }
}

@Preview
@Composable
private fun NewTagFrenchPreview() {
    EasyChatTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimensions.micro),
            contentAlignment = Alignment.Center,
        ) {
            NewTag(
                title = "Nouveau",
                isRounded = true,
            )
        }
    }
}
