package com.alithya.features.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.ui.components.AlertMessage
import com.alithya.common.ui.components.Brand
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.components.ButtonSecondary
import com.alithya.common.ui.components.CircularProgressBar
import com.alithya.common.ui.components.TextFieldCreator
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.RoundedCornerShapeTop
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.features.login.R
import timber.log.Timber

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

@Composable
fun LoginScreen(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val errors = rememberSaveable { mutableStateOf<Int?>(null) }

    val onNavigateToRegister = {
        navController.popBackStack()
        navController.navigate(ScreenTarget.Register.route)
    }

    val onSignInClickHandler = { email: String, password: String ->
        errors.value = null
        viewModel.onEvent(LoginEvent.OnLogin(email, password))
    }

    val onForgotPasswordClickHandler = {
        navController.popBackStack()
        navController.navigate(ScreenTarget.ForgotPassword.route)
    }

    LaunchedEffect(key1 = state) {
        when (val currentState = state) {
            is LoginState.Success -> {
                navController.popBackStack()
                navController.navigate(ScreenTarget.App.route)
            }

            is LoginState.Error -> {
                Timber.d("104 - LoginScreen cause : ${currentState.message}")
                errors.value = currentState.message
            }

            else -> Unit
        }
    }


    Content(
        windowSize = windowSize,
        state = state,
        errors = errors.value,
        onNavigateToRegister = onNavigateToRegister,
        onForgotPasswordClickHandler = onForgotPasswordClickHandler,
        onSignInClickHandler = onSignInClickHandler,
    )
}

@Composable
private fun Content(
    windowSize : WindowWidthSizeClass,
    state: LoginState,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClickHandler: () -> Unit,
    onSignInClickHandler: (String, String) -> Unit,
    errors : Int?
){
    val configuration = LocalConfiguration.current

    if (windowSize == WindowWidthSizeClass.Expanded && configuration.smallestScreenWidthDp > 600
        && configuration.smallestScreenWidthDp > configuration.screenHeightDp) {
        LargeScreen(
            state = state,
            loginErrorMessage = errors,
            onNavigateToRegister = onNavigateToRegister,
            onForgotPasswordClicked = onForgotPasswordClickHandler,
            onSignInClicked = onSignInClickHandler,
        )
    } else {
        SmallScreen(
            state = state,
            loginErrorMessage = errors,
            onNavigateToRegister = onNavigateToRegister,
            onForgotPasswordClicked = onForgotPasswordClickHandler,
            onSignInClicked = onSignInClickHandler,
        )
    }
}

@Composable
fun SmallScreen(
    state: LoginState,
    loginErrorMessage: Int?,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignInClicked: (String, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
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
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Forms(
                loginErrorMessage = loginErrorMessage,
                onNavigateToRegister = onNavigateToRegister,
                onForgotPasswordClicked = onForgotPasswordClicked,
                onSignInClicked = onSignInClicked
            )
        }
    }
    CircularProgressBar(state is LoginState.Loading)
}

@Composable
fun LargeScreen(
    state: LoginState,
    loginErrorMessage: Int?,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignInClicked: (String, String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .weight(1f)
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
        Box(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = MaterialTheme.dimensions.paddingSemiXLarge,
                        start = MaterialTheme.dimensions.paddingXLarge,
                        end = MaterialTheme.dimensions.paddingMedium,
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Forms(
                    loginErrorMessage = loginErrorMessage,
                    isLandScape = true,
                    onNavigateToRegister = onNavigateToRegister,
                    onForgotPasswordClicked = onForgotPasswordClicked,
                    onSignInClicked = onSignInClicked,
                )
                Column(
                    modifier = Modifier
                        .padding(
                            top = MaterialTheme.dimensions.paddingLargeExtra,
                            start = MaterialTheme.dimensions.medium,
                            end = MaterialTheme.dimensions.medium,
                            bottom = MaterialTheme.dimensions.paddingSemiXLarge,
                        ),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingSmall),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.login_copyright_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(id = R.string.login_policy_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            CircularProgressBar(state is LoginState.Loading)
        }
    }
}

@Composable
fun Forms(
    loginErrorMessage: Int?,
    isLandScape: Boolean = false,
    onNavigateToRegister: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onSignInClicked: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimensions.xlarge,
                vertical = MaterialTheme.dimensions.medium
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge),
    ) {
        val email = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.micro)
                .testTag("loginTitleQa"),
            text = stringResource(id = R.string.login_main_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayMedium,
        )

        if (loginErrorMessage != null) {
            AlertMessage(message = stringResource(id = loginErrorMessage))
        }

        TextFieldCreator(
            value = email.value,
            onValueChange = { email.value = it },
            titleName = stringResource(id = R.string.login_email_title),
            placeholder = stringResource(id = R.string.login_email_hint),
            testTag = "loginEmailQa",
            isError = loginErrorMessage != null,
        )

        PasswordAndForgotPassword(
            password = password,
            hasError = loginErrorMessage != null,
            onPasswordChange = { password.value = it },
            onForgotPasswordClicked = { onForgotPasswordClicked() }
        )

        ButtonPrimary(
            modifier =
            if (!isLandScape) {
                Modifier.padding(top = MaterialTheme.dimensions.medium)
            } else {
                Modifier
                    .width(MaterialTheme.dimensions.buttonWidthDefault)
                    .padding(top = MaterialTheme.dimensions.medium)
            },
            text = stringResource(id = R.string.login_buttonSignIn_action).uppercase(),
            testTag = "loginSignInButtonQa",
            onClick = { onSignInClicked(email.value, password.value) }
        )

        Register(
            onNavigateToRegister = onNavigateToRegister,
            isLandScape = isLandScape,
            hasError = loginErrorMessage != null,
        )
    }
}

@Composable
fun PasswordAndForgotPassword(
    password: MutableState<String>,
    hasError: Boolean,
    onPasswordChange: (String) -> Unit,
    onForgotPasswordClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.semiLarge)
    ) {
        TextFieldCreator(
            modifier = Modifier.width(MaterialTheme.dimensions.textFieldWidthMedium),
            value = password.value,
            onValueChange = onPasswordChange,
            titleName = stringResource(id = R.string.login_password_title),
            placeholder = stringResource(id = R.string.login_password_hint),
            testTag = "loginPasswordQa",
            isError = hasError,
            isPassword = true,
        )

        Text(
            text = stringResource(id = R.string.login_forgetPassword_title),
            modifier = Modifier
                .testTag("loginForgotPassword")
                .clickable { onForgotPasswordClicked() },
            style = MaterialTheme.typography.labelMedium,
            lineHeight = MaterialTheme.dimensions.fontSizeXXLarge,
            fontWeight = FontWeight(700),
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Register(
    onNavigateToRegister: () -> Unit,
    isLandScape: Boolean = false,
    hasError: Boolean,
) {
    Column(
        modifier = Modifier.padding(top = if (!hasError) MaterialTheme.dimensions.large else MaterialTheme.dimensions.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMicro)
    ) {
        Text(
            modifier = Modifier.testTag("loginRegisterQa-title"),
            text = stringResource(id = R.string.login_register_title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        ButtonSecondary(
            modifier =
            if (isLandScape) {
                Modifier
                    .width(MaterialTheme.dimensions.buttonWidthDefault)
                    .padding(top = MaterialTheme.dimensions.small)
            } else Modifier,
            text = stringResource(id = R.string.login_buttonRegister_action).uppercase(),
            testTag = "loginRegisterButtonQa",
            onClick = onNavigateToRegister
        )
    }
}

@AllPreview
@Composable
private fun ScreenPreview() {
    EasyChatTheme(dynamicColor = false) {
        Content(
            windowSize = WindowWidthSizeClass.Expanded,
            state = LoginState.Idle,
            onNavigateToRegister = { },
            onForgotPasswordClickHandler = {},
            onSignInClickHandler = { _, _ -> },
            errors = null
        )
    }
}