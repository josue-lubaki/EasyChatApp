package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alithya.common.ui.models.TabOptionModel
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Composable
fun Tabs(
    options : List<TabOptionModel>,
    selected : TabOptionModel = TabOptionModel.Courses,
    onOptionSelected : (TabOptionModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(
                min = MaterialTheme.dimensions.semiXXLarge,
                max = MaterialTheme.dimensions.xxxlarge
            )
            .clip(shape = MaterialTheme.shapes.large)
            .border(
                width = MaterialTheme.dimensions.borderStrokeTiny,
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            TabOption(
                modifier = Modifier.weight(1f),
                title = stringResource(id = option.title),
                icon = option.icon,
                selected = option == selected,
                onClick = {
                    onOptionSelected(option)
                }
            )
        }
    }
}

@Composable
private fun TabOption(
    modifier: Modifier = Modifier,
    title: String,
    icon : ImageVector,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxSize()
            .background(
                color =
                    if (selected) MaterialTheme.colorScheme.tertiary
                    else Color.Transparent,
                shape = MaterialTheme.shapes.large
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.small),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview
@Composable
private fun TabsPreview() {
    val selected = remember { mutableStateOf<TabOptionModel>(TabOptionModel.Courses) }

    EasyChatTheme {
        Tabs(
            options = listOf(
                TabOptionModel.Courses,
                TabOptionModel.People
            ),
            selected = selected.value,
            onOptionSelected = {
                selected.value = it
            }
        )
    }
}