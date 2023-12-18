package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.models.ProfileCellModel
import com.alithya.common.ui.themes.EasyChatTheme

@Composable
fun ProfileCell(
    modifier: Modifier = Modifier,
    option: ProfileCellModel,
    testTag: String,
    onNavigateToRoute: (route : String) -> Unit
) {
    Row(
        modifier = modifier
            .testTag(testTag)
            .requiredHeight(48.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onNavigateToRoute(option.screen.route) })
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(30.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = null,
                imageVector = option.icon
            )

            Text(
                text = stringResource(id = option.text),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Icon(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterVertically),
            tint = Color(0xFF3C3C43).copy(alpha = 0.5f),
            contentDescription = null,
            imageVector = Icons.Filled.ArrowForwardIos
        )
    }
}

@Preview
@Composable
private fun ProfileCellPreview() {
    EasyChatTheme(dynamicColor = false){
        ProfileCell(
            option = ProfileCellModel.AccountInformation,
            onNavigateToRoute = {},
            testTag = "ProfileCell"
        )
    }
}