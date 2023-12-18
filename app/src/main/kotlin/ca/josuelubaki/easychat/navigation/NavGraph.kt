package ca.josuelubaki.easychat.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ca.josuelubaki.easychat.presentation.AppScreen
import ca.josuelubaki.easychat.presentation.SplashScreen
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.utils.Constants.FADE_DURATION_DEFAULT
import com.alithya.features.login.presentation.LoginViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.viewmodel.ext.android.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController : NavHostController,
    windowSize: WindowWidthSizeClass,
) {

    AnimatedNavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = ScreenTarget.Splash.route,
        enterTransition = {
            fadeIn(animationSpec = tween(FADE_DURATION_DEFAULT))
        },
        exitTransition ={
            fadeOut(animationSpec = tween(FADE_DURATION_DEFAULT))
        }
    ){
        composable(ScreenTarget.Splash.route){
            SplashScreen(navController = navController)
        }

        authNavigationGraph(
            navController = navController,
            windowSize = windowSize,
        )

        composable(ScreenTarget.App.route){
            AppScreen(
                rootNavController = navController,
                windowSize = windowSize
            )
        }
    }
}

