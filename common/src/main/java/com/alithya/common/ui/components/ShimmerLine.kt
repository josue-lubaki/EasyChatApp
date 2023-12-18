package com.alithya.common.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Composable
fun ShimmerLine(
    modifier: Modifier = Modifier,
    alpha: Float,
    widthFraction: Float = 1f,
    heightSurface: Dp = MaterialTheme.dimensions.paddingMicro,
    heightSpacer: Dp = MaterialTheme.dimensions.paddingMicro,
    color: Color = MaterialTheme.colorScheme.onBackground,

    ) {
    Surface(
        modifier = modifier
            .alpha(alpha = alpha)
            .fillMaxWidth(widthFraction)
            .height(heightSurface),
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(size = MaterialTheme.dimensions.paddingMicro)
    ){}
    Spacer(modifier = Modifier.height(heightSpacer))
}