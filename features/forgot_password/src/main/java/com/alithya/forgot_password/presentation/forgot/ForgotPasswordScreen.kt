package com.alithya.forgot_password.presentation.forgot

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.derivedStateOf
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
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.components.ButtonSecondary
import com.alithya.common.ui.components.CircularProgressBar
import com.alithya.common.ui.components.TextFieldCreator
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.RoundedCornerShapeTop
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.AllPreview
import com.alithya.common.utils.Constants
import com.alithya.features.forgot_password.R
import org.koin.androidx.compose.koinViewModel

/**
 * created by Josue Lubaki
 * date : 2023-10-05
 * version : 1.0.0
 */

@Composable
fun ForgotPasswordScreen(
    navController : NavHostController,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val errorMessage = rememberSaveable { mutableStateOf<Int?>(null) }
    val email = rememberSaveable { mutableStateOf<String?>(null) }

    val configuration = LocalConfiguration.current

    val onNavigateToRegisterHandler = {
        navController.popBackStack()
        navController.navigate(ScreenTarget.Register.route)
    }

    val onNextButtonClickedHandler = { emailValue : String ->
        viewModel.onEvent(ForgotPasswordEvent.OnForgotPassword(emailValue))
        email.value = emailValue
    }

    LaunchedEffect(key1 = state) {
        when (state) {
            is ForgotPasswordState.Success -> {
                navController.popBackStack()
                navController.navigate(ScreenTarget.ResetPassword.passArgs(email.value!!))
            }

            is ForgotPasswordState.Error -> {
                errorMessage.value = (state as ForgotPasswordState.Error).error
            }

            else -> Unit
        }
    }

    if (windowSize == WindowWidthSizeClass.Expanded && configuration.smallestScreenWidthDp > 600) {
        LargeScreen(
            state = state,
            onNavigateToRegister = onNavigateToRegisterHandler,
            onNextButtonClicked = onNextButtonClickedHandler
        )
    }
    else {
        SmallScreen(
            state = state,
            onNavigateToRegister = onNavigateToRegisterHandler,
            onNextButtonClicked = onNextButtonClickedHandler
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
fun LargeScreen(
    state : ForgotPasswordState,
    onNavigateToRegister: () -> Unit,
    onNextButtonClicked: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center
    ){
        Box(modifier = Modifier
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
                        top = MaterialTheme.dimensions.paddingXXXXLarge,
                        start = MaterialTheme.dimensions.paddingXLarge,
                        end = MaterialTheme.dimensions.paddingMedium,
                        bottom = MaterialTheme.dimensions.paddingLarge
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Forms(
                    onNavigateToRegister = onNavigateToRegister,
                    onNextButtonClicked = onNextButtonClicked,
                    windowSize = WindowWidthSizeClass.Expanded,
                )
                CircularProgressBar(state is ForgotPasswordState.Loading)
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = MaterialTheme.dimensions.medium,
                            vertical = MaterialTheme.dimensions.large
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingMicro)
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

@Composable
fun SmallScreen(
    state : ForgotPasswordState,
    onNavigateToRegister: () -> Unit,
    onNextButtonClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
    ) {
        Brand(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimensions.semiLarge),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShapeTop)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Forms(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensions.semiXXLarge),
                onNavigateToRegister = onNavigateToRegister,
                onNextButtonClicked = onNextButtonClicked
            )
        }
    }
    CircularProgressBar(state is ForgotPasswordState.Loading)
}

@Composable
fun Forms(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNextButtonClicked: (String) -> Unit,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact
) {
    Column(
        modifier = modifier.padding(horizontal = MaterialTheme.dimensions.xlarge),
        verticalArrangement = Arrangement.spacedBy(
            if(windowSize == WindowWidthSizeClass.Expanded) MaterialTheme.dimensions.large
            else MaterialTheme.dimensions.semiLarge
        ),
    ){
        val email = rememberSaveable { mutableStateOf("") }
        val isFormValid = remember {
            derivedStateOf {
                email.value.matches(Constants.REGEX_ALITHYA_MAIL.toRegex())
            }
        }

        Text(
            modifier = Modifier.testTag("forgotPasswordTitleQa"),
            text = stringResource(id = R.string.forgot_password_main_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimensions.micro)
                .testTag("forgotPasswordSubtitleQa"),
            text = stringResource(id = R.string.forgot_password_main_subtitle),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
        )

        Column {
            TextFieldCreator(
                value = email.value,
                onValueChange = { email.value = it },
                testTag = "forgotPasswordEmailQa",
                titleName = stringResource(id = R.string.forgot_password_email_title),
                placeholder = stringResource(id = R.string.forgot_password_email_placeholder),
                isError = isFormValid.value.not() && email.value.isNotBlank(),
            )

            if(isFormValid.value.not() && email.value.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimensions.small),
                    text = stringResource(R.string.invalid_email_address),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        ButtonPrimary(
            modifier = if(windowSize == WindowWidthSizeClass.Expanded){
                Modifier
                    .width(MaterialTheme.dimensions.buttonWidthDefault)
                    .padding(top = MaterialTheme.dimensions.medium)
            } else Modifier.padding(top = MaterialTheme.dimensions.small),
            text = stringResource(id = R.string.forgot_password_next_button).uppercase(),
            testTag = "forgotPasswordNextButtonQa",
            enabled = isFormValid.value,
            onClick = { onNextButtonClicked(email.value) }
        )

        Column(
            modifier = Modifier.padding(top = MaterialTheme.dimensions.xxlarge),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.paddingSmall),
        ){
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.dimensions.micro)
                    .testTag("forgotPasswordBackToLoginQa"),
                text = stringResource(id = R.string.forgot_password_not_have_account),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
            )

            ButtonSecondary(
                modifier = if(windowSize == WindowWidthSizeClass.Expanded){
                    Modifier.width(MaterialTheme.dimensions.buttonWidthDefault)
                } else Modifier,
                text = stringResource(id = R.string.forgot_password_register_button).uppercase(),
                testTag = "forgotPasswordRegisterButtonQa",
                onClick = onNavigateToRegister
            )
        }
    }
}

@AllPreview
@Composable
private fun SmallScreenPreview() {
    EasyChatTheme(dynamicColor = false) {
        SmallScreen(
            state = ForgotPasswordState.Idle,
            onNavigateToRegister = {},
            onNextButtonClicked = {}
        )
    }
}