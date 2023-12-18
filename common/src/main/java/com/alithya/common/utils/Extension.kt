package com.alithya.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * created by Josue Lubaki
 * date : 2023-11-09
 * version : 1.0.0
 */

@Composable
fun Int.dp() : Dp = with(LocalDensity.current) { this@dp.toDp() }