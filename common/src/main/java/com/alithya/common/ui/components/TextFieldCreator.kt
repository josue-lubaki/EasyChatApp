package com.alithya.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.themes.Black
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.Gray300
import com.alithya.common.ui.themes.Primary15
import com.alithya.common.ui.themes.White
import com.alithya.common.ui.themes.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldCreator(
    modifier : Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    testTag: String,
    titleName : String,
    titleColor : Color = MaterialTheme.colorScheme.onBackground,
    titleTextStyle: TextStyle = MaterialTheme.typography.labelLarge,
    placeholder: String = "",
    placeholderColor : Color = Primary15,
    contentColor : Color = Primary15,
    inputBackgroundColor : Color = Color.White,
    shape : Shape = RoundedCornerShape(MaterialTheme.dimensions.small),
    keyboardType : KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(MaterialTheme.dimensions.small),
    isError : Boolean = false,
    isPassword : Boolean = false,
    enabled : Boolean = true,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        Text(
            modifier = Modifier.testTag("$testTag-title"),
            text = titleName,
            style = titleTextStyle,
            color = titleColor,
            fontWeight = FontWeight(700)
        )
        if(!isPassword){
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("$testTag-input"),
                value = value,
                onValueChange = onValueChange,
                textStyle= TextStyle(
                    color = contentColor,
                    fontSize = MaterialTheme.dimensions.fontSizeDefault,
                    fontWeight= FontWeight.Normal,
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = placeholderColor,
                        fontSize = MaterialTheme.dimensions.fontSizeDefault,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = contentColor,
                    focusedContainerColor = inputBackgroundColor,
                    unfocusedContainerColor = inputBackgroundColor,
                    disabledContainerColor = inputBackgroundColor,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Black,
                ),
                shape = shape,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = singleLine,
                isError = isError,
                enabled = enabled,
            )
        }
        else {
            val showPasswordState = rememberSaveable { mutableStateOf(false) }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("$testTag-input"),
                value = value,
                onValueChange = onValueChange,
                textStyle= TextStyle(
                    fontWeight= FontWeight.Normal,
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = placeholderColor,
                        fontSize = MaterialTheme.dimensions.fontSizeDefault,                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = contentColor,
                    focusedContainerColor = inputBackgroundColor,
                    unfocusedContainerColor = inputBackgroundColor,
                    disabledContainerColor = inputBackgroundColor,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Black,
                ),
                shape = shape,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (showPasswordState.value) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.testTag("$testTag-IconButton"),
                        onClick = {
                            showPasswordState.value = !showPasswordState.value
                        }
                    ) {
                        Icon(
                            imageVector =
                            if(showPasswordState.value) Icons.Filled.VisibilityOff
                            else Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = Gray300
                        )
                    }
                },
                singleLine = singleLine,
                isError = isError,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextFieldCreatorPreview() {
    EasyChatTheme {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            TextFieldCreator(
                value = "",
                onValueChange = {},
                testTag = "firstName",
                titleName = "First Name",
                titleColor = Black,
                titleTextStyle = MaterialTheme.typography.labelLarge,
                placeholder = "First Name",
                placeholderColor = Black,
                contentColor = Black,
                inputBackgroundColor = White,
                shape = RoundedCornerShape(MaterialTheme.dimensions.small),
                keyboardType = KeyboardType.Text,
                singleLine = true,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.small),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextFieldCreatorPasswordPreview() {
    EasyChatTheme {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            TextFieldCreator(
                value = "",
                onValueChange = {},
                testTag = "firstName",
                titleName = "First Name",
                titleColor = Black,
                titleTextStyle = MaterialTheme.typography.labelLarge,
                placeholder = "First Name",
                placeholderColor = Black,
                contentColor = Black,
                inputBackgroundColor = White,
                shape = RoundedCornerShape(MaterialTheme.dimensions.small),
                keyboardType = KeyboardType.Text,
                singleLine = true,
                isPassword = true,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.small),
            )
        }
    }
}