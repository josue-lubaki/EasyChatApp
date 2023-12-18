package com.alithya.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.alithya.common.R
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.ButtonRoundedCornerShape
import com.alithya.common.ui.themes.dimensions

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@Composable
fun CardFormation(
    modifier : Modifier = Modifier,
    id : String,
    title : String,
    description : String,
    date : String?,
    onSite : String,
    language : String,
    isNew : Boolean = false,
    onNavigateToFormationDetails : (String) -> Unit = { }
) {

    val isOnline = onSite.isNotBlank()
            && onSite.equals(stringResource(R.string.online), true)
    Column(
        modifier = modifier
            .shadow(
                elevation = MaterialTheme.dimensions.small,
                clip = true,
                shape = ButtonRoundedCornerShape
            )
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = { onNavigateToFormationDetails(id) })
            .padding(MaterialTheme.dimensions.semiLarge),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.medium)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                modifier = Modifier.weight(.75f),
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            if(isNew){
                NewTag(
                    modifier = Modifier.padding(start = MaterialTheme.dimensions.medium),
                    title = stringResource(id = R.string.new_tag)
                )
            }
        }

        Text(
            text = description,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Justify
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.micro)
        ){
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.medium),
                verticalAlignment = Alignment.CenterVertically
            ){
                Available(
                    isValidated = isOnline,
                    isValidatedText = stringResource(R.string.online),
                    isNotValidatedText = stringResource(R.string.on_site),
                    isValidatedIcon = Icons.Default.LocationOn,
                    isNotValidatedIcon = Icons.Default.LocationCity
                )
                date?.let {
                    Available(
                        isValidated = it.isNotBlank(),
                        isValidatedText = date,
                        isValidatedIcon = Icons.Outlined.CalendarMonth
                    )
                }
                Available(
                    isValidated = language.isNotBlank(),
                    isValidatedText = language.uppercase(),
                    isValidatedIcon = Icons.Outlined.Language
                )
            }

            if(isOnline){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.medium),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Available(
                        isValidated = true,
                        isValidatedText = stringResource(R.string.on_video),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardFormationOnlinePreview() {
    BoilerPlateTheme {
        CardFormation(
            id = "1",
            title = "Angular - The Complete Guide (2023 Edition)",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed semper nisl. Sed euismod, nisl sit amet ultricies lacinia, nisl nisl aliquam nisl, eu aliquam nisl nisl sit amet nisl. Lorem ipsum dolor sit amet",
            date = "2023-03-23",
            language = "en",
            onSite = "Online",
            isNew = true,
        )
    }
}

@Preview
@Composable
private fun CardFormationOnSitePreview() {
    BoilerPlateTheme {
        CardFormation(
            id = "1",
            title = "Angular - The Complete Guide (2023 Edition)",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed semper nisl. Sed euismod, nisl sit amet ultricies lacinia, nisl nisl aliquam nisl, eu aliquam nisl nisl sit amet nisl. Lorem ipsum dolor sit amet",
            date = "2023-03-23",
            language = "en",
            onSite = "on site",
            isNew = true,
        )
    }
}
