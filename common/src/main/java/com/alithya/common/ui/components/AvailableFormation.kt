package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alithya.common.R
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-03-23
 * version : 1.0.0
 */

@Composable
fun Available(
    isValidated: Boolean,
    isValidatedText: String,
    isNotValidatedText: String = "",
    isValidatedIcon: ImageVector = Icons.Filled.Videocam,
    isNotValidatedIcon: ImageVector = Icons.Filled.Person,
    textColor: Color = MaterialTheme.colorScheme.outline,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.micro),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (isValidated || isNotValidatedText.isNotBlank()) {
            if(isValidated)
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimensions.medium),
                    imageVector = isValidatedIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                )
            else
                Icon(
                    modifier = Modifier.size(MaterialTheme.dimensions.medium),
                    imageVector = isNotValidatedIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                )

            Text(
                text =
                if (isValidated) isValidatedText
                else isNotValidatedText,
                color = textColor,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview
@Composable
private fun AvailablePreview() {
    BoilerPlateTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimensions.medium)
        ) {
            Available(
                isValidated = false,
                isValidatedText = stringResource(id = R.string.on_site),
                isValidatedIcon = Icons.Filled.Place
            )
        }
    }
}