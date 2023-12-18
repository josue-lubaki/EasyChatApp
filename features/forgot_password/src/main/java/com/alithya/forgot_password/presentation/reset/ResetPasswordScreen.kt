package com.alithya.forgot_password.presentation.reset

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.ui.components.Brand
import com.alithya.common.ui.components.ButtonBack
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.components.PasswordChecker
import com.alithya.common.ui.components.TextFieldCreator
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.RoundedCornerShapeTop
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.features.forgot_password.R
import org.koin.androidx.compose.koinViewModel

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    windowSize : WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    email : String?,
    viewModel : ResetPasswordViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val errorMessage = rememberSaveable { mutableStateOf<Int?>(null) }

    val configuration = LocalConfiguration.current

    val onBackPressedHandler = {
        navController.popBackStack()
        navController.navigate(ScreenTarget.Login.route)
    }

    val onConfirmClickedHandler = { tempPassword: String, newPassword: String ->
        viewModel.onEvent(
            ResetPasswordEvent.OnResetPassword(
                email = email.orEmpty(),
                tempPassword = tempPassword,
                newPassword = newPassword
            )
        )
    }

    LaunchedEffect(key1 = state){
        when(state){
            is ResetPasswordState.Success -> {
                navController.popBackStack()
                navController.navigate(ScreenTarget.Login.route)
            }
            is ResetPasswordState.Error -> {
                errorMessage.value = (state as ResetPasswordState.Error).error
            }

            else -> Unit
        }
    }

    if(windowSize == WindowWidthSizeClass.Expanded && configuration.smallestScreenWidthDp > 600) {
        LandscapeScreen(
            onBackPressed = onBackPressedHandler,
            onConfirmClicked = onConfirmClickedHandler
        )
    } else {
        PortraitScreen(
            onBackPressed = onBackPressedHandler,
            onConfirmClicked = onConfirmClickedHandler
        )
    }

    LaunchedEffect(key1 = errorMessage.value){
        errorMessage.value?.let {
            val message = context.getString(it)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun PortraitScreen(
    onBackPressed: () -> Unit,
    onConfirmClicked: (String, String) -> Unit,
) {
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Brand(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimensions.semiLarge)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShapeTop)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FormsReset(
                    modifier = Modifier.padding(vertical = MaterialTheme.dimensions.paddingMedium),
                    onBackPressed = onBackPressed,
                    onConfirmClicked = onConfirmClicked
                )
            }
        }
    }
}

@Composable
fun FormsReset(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    isLandscape : Boolean = false,
    onConfirmClicked: (String, String) -> Unit,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    val temporaryPassword = rememberSaveable { mutableStateOf("") }
    val newPassword = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val passwordCheckerValid = rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val isFormValid = remember(
        newPassword.value,
        confirmPassword.value,
        passwordCheckerValid.value
    ) {
        temporaryPassword.value.isNotBlank() &&
                newPassword.value.isNotBlank() &&
                confirmPassword.value.isNotBlank() &&
                newPassword.value == confirmPassword.value &&
                passwordCheckerValid.value
    }

    Column(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.xlarge),
        verticalArrangement = Arrangement.spacedBy(
            if(windowSize == WindowWidthSizeClass.Expanded) MaterialTheme.dimensions.large
            else MaterialTheme.dimensions.semiLarge
        ),
    ){
        ButtonBack(
            text = stringResource(id = R.string.forgot_password_back_button),
            testTag = "backToLoginQa",
            onClick = { onBackPressed() },
        )

        Text(
            modifier = Modifier.testTag("forgotPasswordTitleQa"),
            text = stringResource(id = R.string.forgot_password_main_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.micro)
                .testTag("forgotPasswordResetSubtitleQa"),
            text = stringResource(id = R.string.forgot_password_reset_subtitle),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelSmall,
            lineHeight = MaterialTheme.dimensions.fontSizeSmall
        )

        Row(
            modifier = Modifier.testTag("forgotPasswordResetAlertQa"),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .testTag("forgotPasswordResetAlertQa-icon")
                    .padding(end = MaterialTheme.dimensions.paddingMicro),
                imageVector = Icons.Default.Report,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.testTag("forgotPasswordResetAlertQa-text"),
                text = stringResource(id = R.string.forgot_password_reset_alert_message),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelSmall,
            )
        }

        TextFieldCreator(
            value = temporaryPassword.value,
            onValueChange = { temporaryPassword.value = it },
            testTag = "temporaryPasswordQa",
            titleName = stringResource(id = R.string.forgot_password_temporary_password),
            placeholder = stringResource(id = R.string.forgot_password_temporary_password_placeholder),
        )

        TextFieldCreator(
            value = newPassword.value,
            onValueChange = { newPassword.value = it },
            testTag = "newPasswordQa",
            isPassword = true,
            titleName = stringResource(id = R.string.forgot_password_new_password),
            placeholder = stringResource(id = R.string.forgot_password_new_password_placeholder),
            isError = (newPassword.value.isBlank() || !passwordCheckerValid.value) && interactionSource.collectIsFocusedAsState().value
        )

        PasswordChecker(
            password = newPassword.value,
            onValidPassword = { passwordCheckerValid.value = it },
        )

        Column {
            TextFieldCreator(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                testTag = "confirmPasswordQa",
                isPassword = true,
                titleName = stringResource(id = R.string.forgot_password_confirm_password),
                placeholder = stringResource(id = R.string.forgot_password_confirm_password_placeholder),
                isError = (confirmPassword.value.isBlank() || newPassword.value != confirmPassword.value)
                        && interactionSource.collectIsFocusedAsState().value
            )

            if (newPassword.value != confirmPassword.value && confirmPassword.value.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimensions.paddingMicro),
                    text = stringResource(id = R.string.forgot_password_passwords_do_not_match),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

        ButtonPrimary(
            modifier =
            if(!isLandscape) Modifier
            else {
                Modifier
                    .width(MaterialTheme.dimensions.buttonWidthDefault)
                    .padding(top = MaterialTheme.dimensions.paddingMicro)
            },
            text = stringResource(id = R.string.forgot_password_confirm_button).uppercase(),
            onClick = { onConfirmClicked(temporaryPassword.value, newPassword.value) },
            testTag = "forgotPasswordConfirmButtonQa",
            enabled = isFormValid
        )
    }
}

@Composable
fun LandscapeScreen(
    onBackPressed: () -> Unit,
    onConfirmClicked: (String, String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .weight(1F)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(R.drawable.ic_background),
                contentDescription = stringResource(R.string.background_alithya)
            )

            Brand(
                modifier = Modifier.fillMaxSize(),
                isLandScape = true
            )
        }

        Box(modifier = Modifier
            .weight(1F)
            .background(MaterialTheme.colorScheme.background)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = MaterialTheme.dimensions.paddingXLarge,
                        end = MaterialTheme.dimensions.paddingMedium,
                    )
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                FormsReset(
                    modifier = Modifier.padding(top = MaterialTheme.dimensions.semiXXLarge),
                    isLandscape = true,
                    onBackPressed = onBackPressed,
                    onConfirmClicked = onConfirmClicked,
                )
                Column(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimensions.xxxlarge,
                            bottom = MaterialTheme.dimensions.large,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMicro),
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot_password_copyright_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(id = R.string.forgot_password_policy_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@AllPreview
@Composable
private fun PortraitScreenPreview() {
    EasyChatTheme(dynamicColor = false) {
        PortraitScreen(
            onBackPressed = { },
            onConfirmClicked = { _, _ -> }
        )
    }
}