package com.alithya.features.register.presentation

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.ui.components.AlertMessage
import com.alithya.common.ui.components.Brand
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.components.ButtonSecondary
import com.alithya.common.ui.components.CircularProgressBar
import com.alithya.common.ui.components.PasswordChecker
import com.alithya.common.ui.components.TextFieldCreator
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.common.ui.themes.RoundedCornerShapeTop
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.common.utils.Constants
import com.alithya.features.register.R
import org.koin.androidx.compose.koinViewModel

/**
 * created by josuelubaki
 * date : 2023-10-04
 * version : 1.0.0
 */

@Composable
fun RegisterScreen(
    navController : NavHostController,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val configuration = LocalConfiguration.current

    val state by viewModel.state.collectAsState()
//    val sideEffect = viewModel.sideEffect
    val errors = rememberSaveable { mutableStateOf<Int?>(null) }

    val onNavigateToLoginClickedHandler = {
        navController.popBackStack()
        navController.navigate(ScreenTarget.Login.route)
    }

    val onRegisterClickedHandler = { firstname : String, lastname : String, email: String, password: String ->
        errors.value = null
        viewModel.onEvent(
            RegisterEvent.OnRegister(
                firstname = firstname,
                lastname = lastname,
                email = email,
                password = password
            )
        )
    }

    LaunchedEffect(key1 = state){
        when(val currentState = state){
            is RegisterState.Success -> onNavigateToLoginClickedHandler()
            is RegisterState.Error -> {
                errors.value = currentState.message
            }
            else -> Unit
        }
    }

    if (windowSize == WindowWidthSizeClass.Expanded && configuration.smallestScreenWidthDp > 600) {
        LargeScreen(
            state = state,
            registerErrorMessage = errors.value,
            onNavigateToLoginClicked = onNavigateToLoginClickedHandler,
            onRegisterClicked = onRegisterClickedHandler,
        )
    }
    else {
        SmallScreen(
            state = state,
            registerErrorMessage = errors.value,
            onNavigateToLoginClicked = onNavigateToLoginClickedHandler,
            onRegisterClicked = onRegisterClickedHandler,
        )
    }
}

@Composable
fun SmallScreen(
    state: RegisterState,
    registerErrorMessage: Int?,
    onNavigateToLoginClicked: () -> Unit,
    onRegisterClicked: (String, String, String, String) -> Unit,
) {
    val scrollState = rememberScrollState()

    if(registerErrorMessage != null){
        LaunchedEffect(key1 = true){
            scrollState.animateScrollTo(0)
        }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ){
            Brand(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShapeTop)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Forms(
                    registerErrorMessage = registerErrorMessage,
                    onNavigateToLoginClicked = onNavigateToLoginClicked,
                    onRegisterClicked = onRegisterClicked
                )
            }
        }
        CircularProgressBar(state is RegisterState.Loading)
    }
}

@Composable
fun LargeScreen(
    state: RegisterState,
    registerErrorMessage: Int?,
    onNavigateToLoginClicked: () -> Unit,
    onRegisterClicked: (String, String, String, String) -> Unit,
) {
    val scrollState = rememberScrollState()

    if(registerErrorMessage != null){
        LaunchedEffect(key1 = true){
            scrollState.animateScrollTo(0)
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
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
        ) {
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
                Forms(
                    registerErrorMessage = registerErrorMessage,
                    isLandscape = true,
                    onRegisterClicked = onRegisterClicked,
                    onNavigateToLoginClicked = onNavigateToLoginClicked,
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
                        text = stringResource(id = R.string.register_copyright_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(id = R.string.register_policy_msg).uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            CircularProgressBar(state is RegisterState.Loading)
        }
    }
}

@Composable
fun Forms(
    isLandscape: Boolean = false,
    registerErrorMessage: Int?,
    onRegisterClicked: (String, String, String, String) -> Unit,
    onNavigateToLoginClicked: () -> Unit
) {
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordCheckerValid = rememberSaveable { mutableStateOf(false) }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val emailValid = rememberSaveable { mutableStateOf(true) }

    val isFormValid = remember(firstName.value, lastName.value, email.value,
        password.value, confirmPassword.value) {
        firstName.value.isNotBlank() && lastName.value.isNotBlank() &&
                email.value.isNotBlank() && password.value.isNotBlank() &&
                confirmPassword.value.isNotBlank() && password.value == confirmPassword.value
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.dimensions.large,
                end = MaterialTheme.dimensions.large,
                top =
                if (!isLandscape) MaterialTheme.dimensions.paddingLarge
                else MaterialTheme.dimensions.xxxxxlarge,
                bottom =
                if (!isLandscape) MaterialTheme.dimensions.paddingLargeExtra
                else MaterialTheme.dimensions.paddingMedium
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMedium),
    ) {

        Text(
            modifier = Modifier.testTag("registerTitleQa"),
            text =
            if(!isLandscape) stringResource(id = R.string.register_main_title)
            else stringResource(id = R.string.register_main_title_without_break),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displayMedium,
        )

        if (registerErrorMessage != null) {
            AlertMessage(message = stringResource(id = registerErrorMessage))
        }

        if(!isLandscape){
            TextFieldCreator(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                titleName = stringResource(id = R.string.register_first_name_field_title),
                placeholder = stringResource(id = R.string.register_first_name_placeholder),
                testTag = "registerFirstNameQa",
                isError = firstName.value.isBlank() && interactionSource.collectIsFocusedAsState().value
            )

            TextFieldCreator(
                value = lastName.value,
                onValueChange = { lastName.value = it },
                titleName = stringResource(id = R.string.register_last_name_field_title),
                placeholder = stringResource(id = R.string.register_last_name_placeholder),
                testTag = "registerLastNameQa",
                isError = lastName.value.isBlank() && interactionSource.collectIsFocusedAsState().value
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMedium)
            ){
                TextFieldCreator(
                    modifier = Modifier.weight(1f),
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    titleName = stringResource(id = R.string.register_first_name_field_title),
                    placeholder = stringResource(id = R.string.register_first_name_placeholder),
                    testTag = "registerFirstNameQa",
                    isError = firstName.value.isBlank() && interactionSource.collectIsFocusedAsState().value
                )

                TextFieldCreator(
                    modifier = Modifier.weight(1f),
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    titleName = stringResource(id = R.string.register_last_name_field_title),
                    placeholder = stringResource(id = R.string.register_last_name_placeholder),
                    testTag = "registerLastNameQa",
                    isError = lastName.value.isBlank() && interactionSource.collectIsFocusedAsState().value
                )
            }
        }

        Column {
            TextFieldCreator(
                value = email.value,
                onValueChange = {
                    email.value = it
                    emailValid.value = it.isNotBlank() && it.matches(Constants.REGEX_ALITHYA_MAIL.toRegex())
                },
                titleName = stringResource(id = R.string.register_email_field_title),
                placeholder = stringResource(id = R.string.register_email_placeholder),
                testTag = "registerEmailQa",
                isError = !emailValid.value
            )

            if(!emailValid.value){
                Text(
                    text = stringResource(R.string.invalid_email_address),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        TextFieldCreator(
            value = password.value,
            onValueChange = { password.value = it },
            titleName = stringResource(id = R.string.register_password_field_title),
            placeholder = stringResource(id = R.string.register_password_placeholder),
            testTag = "registerPasswordQa",
            isPassword = true,
            isError = (password.value.isBlank() || !passwordCheckerValid.value) && interactionSource.collectIsFocusedAsState().value
        )

        PasswordChecker(
            password = password.value,
            onValidPassword = { passwordCheckerValid.value = it },
        )

        Column {
            TextFieldCreator(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensions.medium),
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                titleName = stringResource(id = R.string.register_confirm_password_field_title),
                placeholder = stringResource(id = R.string.register_confirm_password_placeholder),
                testTag = "registerConfirmPasswordQa",
                isPassword = true,
                isError = confirmPassword.value.isBlank() && interactionSource.collectIsFocusedAsState().value ||
                        confirmPassword.value != password.value && confirmPassword.value.isNotBlank()
            )

            if(confirmPassword.value != password.value && confirmPassword.value.isNotBlank()){
                Text(
                    text = stringResource(R.string.passwords_do_not_match),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        ButtonPrimary(
            modifier =
            if(!isLandscape) Modifier
            else {
                Modifier
                    .width(MaterialTheme.dimensions.buttonWidthDefault)
                    .padding(bottom = MaterialTheme.dimensions.xlarge)
            },
            text = stringResource(R.string.create_account).uppercase(),
            testTag = "createAccountQa",
            enabled = isFormValid,
            onClick = {
                onRegisterClicked(
                    firstName.value,
                    lastName.value,
                    email.value,
                    password.value
                )
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMicro)
        ){
            Text(
                modifier = Modifier.testTag("registerAlreadyHaveAnAccountQa"),
                text = stringResource(R.string.already_have_an_account),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            ButtonSecondary(
                modifier =
                if(!isLandscape) Modifier
                else Modifier.width(MaterialTheme.dimensions.buttonWidthSmall),
                text = stringResource(R.string.login).uppercase(),
                testTag = "LoginButtonQa",
                onClick = onNavigateToLoginClicked
            )
        }

    }
}

@AllPreview
@Composable
private fun ScreenPreview() {
    BoilerPlateTheme(dynamicColor = false) {
        SmallScreen(
            state = RegisterState.Idle,
            registerErrorMessage = null,
            onNavigateToLoginClicked = { },
        ) { _, _, _, _ -> }
    }
}