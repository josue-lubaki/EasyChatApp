package com.alithya.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.alithya.common.R
import com.alithya.common.ui.themes.EasyChatTheme

@Composable
fun ProfilePicture(
    modifier : Modifier = Modifier,
    pictureUrl : String? = null,
    imageSize : Dp = 120.dp
) {
    if (pictureUrl.isNullOrEmpty()) {
        DefaultProfile(modifier, imageSize)
    } else {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape)
                .size(imageSize),
            contentAlignment = Alignment.Center
        ) {

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pictureUrl)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build()
            )

            if(painter.state is AsyncImagePainter.State.Success) {
                Image(
                    painter = painter,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(imageSize),
                    contentScale = ContentScale.Crop,
                )
            }
            else {
                DefaultProfile(modifier, imageSize)
            }
        }
    }
}

@Composable
private fun DefaultProfile(
    modifier: Modifier,
    imageSize: Dp
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color.White, CircleShape)
            .size(imageSize)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF176DFC),
                        Color(0xFF0F52C0),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Preview
@Composable
private fun ProfilePicturePreview() {
    EasyChatTheme(dynamicColor = false) {
        ProfilePicture()
    }
}