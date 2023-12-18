package ca.josuelubaki.easychat.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ca.josue_lubaki.loginatp.presentation.screens.LoginATPScreen
import ca.josue_lubaki.loginatp.presentation.screens.LoginOTPScreen
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.utils.Constants.EMAIL_ARGUMENT_KEY
import com.alithya.features.register.presentation.RegisterScreen
import com.alithya.forgot_password.presentation.forgot.ForgotPasswordScreen
import com.alithya.forgot_password.presentation.reset.ResetPasswordScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

/**
 * created by Josue Lubaki
 * date : 2023-09-08
 * version : 1.0.0
 */

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavigationGraph(
    navController: NavHostController,
    windowSize: WindowWidthSizeClass,
) {
    navigation(
        route = Graph.AUTH,
        startDestination = ScreenTarget.LoginATP.route
    ) {
        composable(ScreenTarget.LoginATP.route) {
            LoginATPScreen(
                navController = navController,
//                windowSize = windowSize,
            )
        }

        composable(ScreenTarget.LoginOTP.route) {
            LoginOTPScreen(
                navController = navController,
//                windowSize = windowSize,
            )
        }

        composable(ScreenTarget.Register.route) {
            RegisterScreen(
                navController = navController,
                windowSize = windowSize
            )
        }

        composable(ScreenTarget.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                windowSize = windowSize
            )
        }

        composable(
            route = ScreenTarget.ResetPassword.route,
            arguments = listOf(navArgument(EMAIL_ARGUMENT_KEY) { type = NavType.StringType})
        ) {backStackEntry ->
            val email = backStackEntry.arguments?.getString(EMAIL_ARGUMENT_KEY)
            ResetPasswordScreen(
                navController = navController,
                windowSize = windowSize,
                email = email
            )
        }
    }
}