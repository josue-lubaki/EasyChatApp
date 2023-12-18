package ca.josue_lubaki.loginatp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.josue_lubaki.loginatp.R
import ca.josue_lubaki.loginatp.presentation.components.BubbleItem
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.ui.components.ButtonPrimary
import com.alithya.common.ui.themes.EasyChatTheme

/**
 * created by Josue Lubaki
 * date : 2023-12-18
 * version : 1.0.0
 */

@Composable
fun LoginOTPScreen(
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    navController: NavController,
//    viewModel: LoginATPViewModel = koinViewModel()
) {
//    val state by viewModel.state.collectAsState()

    Content(
        navController = navController
    )
}

@Composable
private fun Content(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val stepCount = 3
                items(stepCount){ index ->
                    BubbleItem(
                        text = "${index + 1}",
                        backgroundColor = if (index == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim,
                        textColor = if (index == 1) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    )

                    if (index < stepCount - 1){
                        Spacer(modifier = Modifier.padding(16.dp))
                    }
                }
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_password),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Enter OTP sent to your phone number",
                letterSpacing = 0.08.sp,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            var otpValue: String by rememberSaveable { mutableStateOf("") }

            TextField(
                value = otpValue,
                onValueChange = {
                    otpValue = it
                },
                label = {
                    Text(text = "OTP")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
            )

            ButtonPrimary(
                text = "Next".uppercase(),
                testTag = "login_next_button",
                shape = MaterialTheme.shapes.extraLarge
            ) {
                if (otpValue.isNotBlank()){
                    navController.navigate(ScreenTarget.App.route)
                }
            }

            CircularProgressIndicator()

            Text(
                text = "Resend OTP in 30 seconds",
                letterSpacing = 0.08.sp,
                fontSize = 14.sp,
                modifier = Modifier
                    .safeGesturesPadding()
                    .padding(bottom = 16.dp)
                    .clickable {
                        // TODO: 2023-12-18 resend OTP
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginOTPPreview() {
    EasyChatTheme {
        Content(
            navController = NavController(LocalContext.current),
        )
    }
}