package com.alithya.settings.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alithya.common.ui.components.ButtonBack
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.components.HeaderNav
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.Primary50
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.common.utils.LocaleUtils.setLocale
import com.alithya.features.settings.R
import java.util.Locale

@Composable
fun SettingScreen(
    windowSize : WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel : SettingViewModel,
    onBackPressed: () -> Boolean,
) {
    val context = LocalContext.current as Activity

    val preferredLanguageStored by viewModel.preferredLanguage.collectAsState()
    val currentLanguage = remember(preferredLanguageStored) { mutableStateOf(preferredLanguageStored) }

    val onSelectedLanguageHandler = { language: String ->
        currentLanguage.value = language
    }

    val onSaveLanguageClickHandler = {
        viewModel.savePreferredLanguage(currentLanguage.value)
        setLocale(context, currentLanguage.value)
        context.recreate()
    }

    if (windowSize == WindowWidthSizeClass.Expanded) {
        SettingLargeScreen(
            currentLanguage = currentLanguage.value,
            onBackPressed = onBackPressed,
            onSaveLanguagePressed = onSaveLanguageClickHandler,
            onSelectedLanguage = onSelectedLanguageHandler,
        )
    } else {
        SettingSmallScreen(
            currentLanguage = currentLanguage.value,
            onBackPressed = onBackPressed,
            onSaveLanguagePressed = onSaveLanguageClickHandler,
            onSelectedLanguage = onSelectedLanguageHandler,
        )
    }
}

@Composable
fun SettingSmallScreen(
    currentLanguage : String,
    onBackPressed: () -> Boolean,
    onSaveLanguagePressed: () -> Unit,
    onSelectedLanguage : (language : String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        HeaderNav(
            title = stringResource(id = R.string.settings),
            onClick = { onBackPressed() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = MaterialTheme.dimensions.large
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ){
            Column(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xlarge),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge),
            ){
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )

                Column {
                    Text(
                        modifier = Modifier.padding(start = MaterialTheme.dimensions.medium),
                        text = stringResource(id = R.string.change_language_select).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        lineHeight = MaterialTheme.dimensions.fontSizeSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(
                                topStart = MaterialTheme.dimensions.large,
                                topEnd = MaterialTheme.dimensions.large
                            ))
                            .background(MaterialTheme.colorScheme.background)
                            .clickable(onClick = { onSelectedLanguage(Locale.FRENCH.language) })
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.padding(MaterialTheme.dimensions.medium),
                            text = stringResource(id = R.string.french),
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        if(currentLanguage == Locale.FRENCH.language){
                            Icon(
                                modifier = Modifier.padding(end = MaterialTheme.dimensions.paddingSmall),
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Primary50,
                            )
                        }

                    }

                    Divider(
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.medium),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(
                                bottomStart = MaterialTheme.dimensions.large,
                                bottomEnd = MaterialTheme.dimensions.large
                            ))
                            .background(MaterialTheme.colorScheme.background)
                            .clickable(onClick = { onSelectedLanguage(Locale.ENGLISH.language) })
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier.padding(MaterialTheme.dimensions.medium),
                            text = stringResource(id = R.string.english),
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        if(currentLanguage == Locale.ENGLISH.language){
                            Icon(
                                modifier = Modifier.padding(
                                    end = MaterialTheme.dimensions.paddingSmall
                                ),
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Primary50,
                            )
                        }
                    }
                }

                ButtonPrimary(
                    modifier = Modifier.padding(top = MaterialTheme.dimensions.medium),
                    text = stringResource(id = R.string.change_language).uppercase(),
                    testTag = "save",
                    onClick = { onSaveLanguagePressed() },
                    enabled = currentLanguage != Locale.getDefault().language
                )
            }
        }
    }
}

@Composable
fun SettingLargeScreen(
    currentLanguage : String,
    onSaveLanguagePressed: () -> Unit,
    onBackPressed: () -> Boolean,
    onSelectedLanguage : (language : String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = MaterialTheme.dimensions.xxxlarge
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {

            ButtonBack(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensions.semiXXLarge),
                text = stringResource(id = R.string.profile),
                testTag = "backButton",
                onClick = { onBackPressed() }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge),
            ) {
                Text(
                    text = stringResource(id = R.string.language),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )

                Column {
                    Text(
                        modifier = Modifier.padding(start = MaterialTheme.dimensions.medium),
                        text = stringResource(id = R.string.change_language_select).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        lineHeight = MaterialTheme.dimensions.fontSizeSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.dimensions.medium)
                            .clip(RoundedCornerShape(
                                topStart = MaterialTheme.dimensions.large,
                                topEnd = MaterialTheme.dimensions.large
                            ))
                            .background(MaterialTheme.colorScheme.background)
                            .clickable(onClick = { onSelectedLanguage(Locale.FRENCH.language) }),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(MaterialTheme.dimensions.medium),
                            text = stringResource(id = R.string.french),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        if (currentLanguage == Locale.FRENCH.language) {
                            Icon(
                                modifier = Modifier.padding(
                                    end = MaterialTheme.dimensions.paddingSmall
                                ),
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Primary50,
                            )
                        }

                    }

                    Divider(
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.medium),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(
                                bottomStart = MaterialTheme.dimensions.large,
                                bottomEnd = MaterialTheme.dimensions.large
                            ))
                            .background(MaterialTheme.colorScheme.background)
                            .clickable(onClick = { onSelectedLanguage(Locale.ENGLISH.language) }),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(MaterialTheme.dimensions.medium),
                            text = stringResource(id = R.string.english),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight(400),
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        if (currentLanguage == Locale.ENGLISH.language) {
                            Icon(
                                modifier = Modifier.padding(end = MaterialTheme.dimensions.paddingSmall),
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Primary50,
                            )
                        }
                    }
                }

                ButtonPrimary(
                    modifier = Modifier
                        .width(MaterialTheme.dimensions.buttonWidthMedium)
                        .padding(top = MaterialTheme.dimensions.medium),
                    text = stringResource(id = R.string.change_language).uppercase(),
                    testTag = "save",
                    onClick = { onSaveLanguagePressed() },
                    enabled = currentLanguage != Locale.getDefault().language
                )
            }
        }
    }
}

@AllPreview
@Composable
private fun ScreenPreview() {
    BoilerPlateTheme {
        SettingSmallScreen(
            currentLanguage = Locale.getDefault().language,
            onBackPressed = { false },
            onSaveLanguagePressed = { },
            onSelectedLanguage = { }
        )
    }
}