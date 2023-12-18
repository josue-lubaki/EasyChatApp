package ca.josuelubaki.easychat.presentation

import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ca.josuelubaki.easychat.R
import ca.josuelubaki.easychat.navigation.Graph
import com.alithya.common.ui.themes.White

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(1.5f) }

    LaunchedEffect(key1 = "splash-scale") {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            scale.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = {
                        it
                    }
                )
            )
        } else {
            scale.animateTo(
                targetValue = 2.5f,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = {
                        it
                    }
                )
            )
        }

        navController.popBackStack()
        navController.navigate(Graph.AUTH)
    }

    Splash(scale.value)
}

@Composable
fun Splash(scale: Float) {
    Box(
        modifier = Modifier
            .background(White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.scale(scale = scale),
            painter = painterResource(id = R.drawable.alithya_logo),
            contentDescription = stringResource(R.string.alithya_logo_icon)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        navController = rememberNavController()
    )
}