package com.alithya.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.alithya.common.R
import com.alithya.common.ui.themes.BRAND_HEIGHT
import com.alithya.common.ui.themes.BRAND_HEIGHT_LANDSCAPE
import com.alithya.common.ui.themes.BRAND_WIDTH
import com.alithya.common.ui.themes.BRAND_WIDTH_LANDSCAPE
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.dimensions

@Composable
fun Brand(
    modifier: Modifier = Modifier,
    isLandScape: Boolean = false,
) {
    val isDarkMode = isSystemInDarkTheme()
    Column(
        modifier = modifier
            .padding(MaterialTheme.dimensions.small),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(
                width = if(!isLandScape) BRAND_WIDTH else BRAND_WIDTH_LANDSCAPE,
                height = if(!isLandScape) BRAND_HEIGHT else BRAND_HEIGHT_LANDSCAPE
            ),
            painter =
                if(isDarkMode) painterResource(R.drawable.ic_alithya_dark)
                else painterResource(R.drawable.ic_alithya),
            contentDescription = stringResource(R.string.alithya_logo),
        )
        Text(
            text = stringResource(id = R.string.login_main_subtitle).uppercase(),
            style = TextStyle(
                fontSize = if(!isLandScape) 16.sp else 36.sp,
                fontWeight = FontWeight.Light
            ),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF176DFC)
@Composable
private fun BrandPreview() {
    EasyChatTheme {
        Brand()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF176DFC)
@Composable
private fun BrandLandScapePreview() {
    EasyChatTheme {
        Brand(isLandScape = true)
    }
}