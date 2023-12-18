package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.ButtonRoundedCornerShape
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Composable
fun CardFormationShimmer(
    alpha: Float,
) {
    Column(
        modifier = Modifier
            .shadow(
                elevation = MaterialTheme.dimensions.small,
                clip = true,
                shape = ButtonRoundedCornerShape
            )
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimensions.semiLarge),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.medium)
    ){

        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            ShimmerLine(
                alpha = alpha,
                heightSurface = MaterialTheme.dimensions.large,
            )

            ShimmerLine(
                alpha = alpha,
                widthFraction = 0.4f,
                heightSurface = MaterialTheme.dimensions.large,
            )
        }

        Column(){
            repeat(4){
                ShimmerLine(
                    alpha = alpha,
                    heightSurface = MaterialTheme.typography.bodyMedium.fontSize.value.dp,
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(.7f),
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.micro)
            ){
                repeat(3){
                    ShimmerLine(
                        modifier = Modifier.weight(1f),
                        alpha = alpha,
                        widthFraction = 0.4f,
                        heightSurface = MaterialTheme.typography.bodyMedium.fontSize.value.dp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.paddingMicro))
            ShimmerLine(
                alpha = alpha,
                widthFraction = 0.3f,
                heightSurface = MaterialTheme.typography.bodyMedium.fontSize.value.dp,
            )
        }
    }
}

@Preview
@Composable
fun CardFormationShimmerPreview() {
    EasyChatTheme {
        AnimateShimmerEffect {
            CardFormationShimmer(alpha = it)
        }
    }
}