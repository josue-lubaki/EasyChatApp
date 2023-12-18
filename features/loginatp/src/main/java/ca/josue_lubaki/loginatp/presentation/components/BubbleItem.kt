package ca.josue_lubaki.loginatp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alithya.common.ui.themes.EasyChatTheme

/**
 * created by Josue Lubaki
 * date : 2023-12-18
 * version : 1.0.0
 */

@Composable
fun BubbleItem(
    modifier: Modifier = Modifier,
    text : String,
    backgroundColor : Color = MaterialTheme.colorScheme.primary,
    textColor : Color = MaterialTheme.colorScheme.onPrimary,
) {

    Box(
        modifier = modifier
            .width(36.dp)
            .height(36.dp)
            .clip(CircleShape)
            .background(
                color = backgroundColor,
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            textAlign = TextAlign.Center,
        )
    }

}

@Preview
@Composable
private fun BubblePreview() {
    EasyChatTheme {
        BubbleItem(
            text = "1"
        )
    }
}