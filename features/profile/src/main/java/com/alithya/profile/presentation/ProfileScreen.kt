package com.alithya.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.alithya.common.domain.models.InfoUser
import com.alithya.common.domain.models.States
import com.alithya.common.ui.components.AlertMessage
import com.alithya.common.ui.components.ButtonLogout
import com.alithya.common.ui.components.CircularProgressBar
import com.alithya.common.ui.components.HeaderNav
import com.alithya.common.ui.components.ProfileCell
import com.alithya.common.ui.components.ProfilePicture
import com.alithya.common.ui.models.ProfileCellModel
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.profile.BuildConfig
import com.alithya.profile.R

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

@Composable
fun ProfileScreen(
    windowSize : WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel : ProfileViewModel,
    onLogoutPressed : () -> Unit,
    onNavigateToRoute : (String) -> Unit,
) {

    val state by viewModel.state.collectAsState()

    val userInfo = remember { mutableStateOf<InfoUser?>(null) }
    val errorMessage = rememberSaveable { mutableStateOf<Int?>(null) }

    LaunchedEffect(key1 = true){
        viewModel.onEvent(ProfileEvent.OnLoadMyProfile)
    }

    LaunchedEffect(state) {
        when (state) {
            is ProfileState.Success -> {
                userInfo.value = (state as ProfileState.Success).user
            }

            is ProfileState.Error -> {
                errorMessage.value = (state as ProfileState.Error).message
            }

            else -> {
                errorMessage.value = null
            }
        }
    }

    val options = listOf(
        ProfileCellModel.AccountInformation,
        ProfileCellModel.WorkSkills,
        ProfileCellModel.Settings,
    )

    if (windowSize == WindowWidthSizeClass.Expanded) {
        LargeScreen(
            state = state,
            errorMessage = errorMessage.value,
            options = options,
            userInfo = userInfo.value,
            onNavigateToRoute = onNavigateToRoute,
            onLogoutPressed = onLogoutPressed
        )
    } else {
        SmallScreen(
            state = state,
            errorMessage = errorMessage.value,
            options = options,
            userInfo = userInfo.value,
            onNavigateToRoute = onNavigateToRoute,
            onLogoutPressed = onLogoutPressed
        )
    }
}

@Composable
fun SmallScreen(
    state : States,
    errorMessage : Int?,
    options: List<ProfileCellModel>,
    userInfo : InfoUser?,
    onNavigateToRoute: (route : String) -> Unit,
    onLogoutPressed: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.scrim)
    ){

        HeaderNav(
            title = stringResource(id = R.string.my_profile),
            icon = { },
            onClick = { }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ){
            Column(
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimensions.xxxlarge,
                        horizontal = MaterialTheme.dimensions.large
                    ),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileInformation(
                    pictureUrl = userInfo?.imageUrl,
                    name = userInfo?.displayName,
                    profession = userInfo?.jobTitle,
                )

                if (errorMessage != null) {
                    AlertMessage(message = stringResource(id = errorMessage))
                }

                Divider(
                    modifier = Modifier.padding(top = MaterialTheme.dimensions.paddingSmall),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.small),
                ){
                    options.forEach { option ->
                        ProfileCell(
                            option = option,
                            onNavigateToRoute = onNavigateToRoute,
                            testTag = option.testTag
                        )
                    }
                }

                Divider(color = MaterialTheme.colorScheme.surfaceVariant)

                ButtonLogout(
                    text = stringResource(R.string.log_out).uppercase(),
                    testTag = "logout_button",
                    textStyle = MaterialTheme.typography.labelLarge,
                    onClick = onLogoutPressed
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text(
                        text= stringResource(R.string.app_name),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.outline
                    )

                    Text(
                        text = BuildConfig.VERSION_NAME,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
    CircularProgressBar(state is ProfileState.Loading)
}

@Composable
fun LargeScreen(
    state : States,
    errorMessage : Int?, // TODO : Handle error message
    options: List<ProfileCellModel>,
    userInfo : InfoUser?,
    onNavigateToRoute: (String) -> Unit,
    onLogoutPressed : () -> Unit
) {
    Column(
        modifier= Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ){
        Row(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimensions.semiLarge,
                horizontal = MaterialTheme.dimensions.large
            ),
            horizontalArrangement = Arrangement.Center,
        ){
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge)
                ){
                    ProfileInformation(
                        pictureUrl = userInfo?.imageUrl,
                        name = userInfo?.displayName,
                        profession = userInfo?.jobTitle,
                    )
                }
            }
            Box(
                modifier = Modifier.weight(2F),
                contentAlignment = Alignment.Center
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = MaterialTheme.dimensions.semiLarge,
                            horizontal = MaterialTheme.dimensions.large
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = MaterialTheme.dimensions.paddingSmall
                    )
                ){
                    options.forEach { option ->
                        ProfileCell(
                            option = option,
                            onNavigateToRoute = onNavigateToRoute,
                            testTag = option.testTag
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(
                vertical = MaterialTheme.dimensions.xlarge,
                horizontal = MaterialTheme.dimensions.semiXXLarge
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.dimensions.paddingSmall
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            ButtonLogout(
                modifier = Modifier.width(MaterialTheme.dimensions.buttonWidthMedium),
                text = stringResource(R.string.log_out).uppercase(),
                testTag = "logout_button",
                containerColor = Color.Transparent,
                onClick = onLogoutPressed
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){

                Text(
                    text= stringResource(R.string.app_name),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
    CircularProgressBar(state is ProfileState.Loading)
}

@Composable
private fun ProfileInformation(
    pictureUrl : String?,
    name : String?,
    profession : String?
) {
    ProfilePicture(pictureUrl = pictureUrl)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        name?.let {
            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall,
                lineHeight = MaterialTheme.dimensions.fontSizeExtraLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        profession?.let {
            Text(
                text = profession,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@AllPreview
@Composable
private fun ScreenPreview() {
    BoilerPlateTheme(dynamicColor = false) {
        SmallScreen(
            state = ProfileState.Idle,
            options = listOf(
                ProfileCellModel.AccountInformation,
                ProfileCellModel.WorkSkills,
                ProfileCellModel.Settings,
            ),
            userInfo = null,
            onNavigateToRoute = { },
            onLogoutPressed = {},
            errorMessage = null
        )
    }
}