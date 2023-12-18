package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    list: List<String>,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    text : String,
    placeholder: String,
    onTextChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onSearchClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var textFieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.large
            )
            .border(
                width = MaterialTheme.dimensions.borderStrokeTiny,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.large
            )
    ) {
        TextField(
            value = text,
            onValueChange = { onTextChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                }
            ,
            placeholder = { Text(text = placeholder) },
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(0.5f),
                    onClick = {
                        onSearchClicked(text)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search_icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            },

            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { onExpandedChange() })
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            ),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            list.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        onTextChange(label)
                        onExpandedChange()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DropDownMenuPreview() {
    val text = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    EasyChatTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            DropDownMenu(
                list = listOf("Montreal", "Toronto", "Vancouver"),
                expanded = expanded.value,
                onDismissRequest = {
                    expanded.value = false
                },
                onExpandedChange = {
                    expanded.value = !expanded.value
                },
                text = text.value,
                onTextChange = {
                    text.value = it
                },
                placeholder = "Select a city",
                onSearchClicked = {
                    expanded.value = false
                },
            )
        }
    }
}