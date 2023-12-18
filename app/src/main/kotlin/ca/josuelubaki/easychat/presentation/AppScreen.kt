@file:OptIn(ExperimentalAnimationApi::class)

package ca.josuelubaki.easychat.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.josuelubaki.easychat.models.BottomMenu
import com.alithya.common.navigation.ScreenTarget
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.common.ui.themes.dimensions
import com.alithya.common.utils.Constants.EXPAND_DURATION
import com.alithya.common.utils.Constants.FADE_DURATION_DEFAULT
import com.alithya.common.utils.Constants.FADE_DURATION_LONG
import com.alithya.common.utils.Constants.UUID_ARGUMENT_KEY
import com.alithya.profile.presentation.ProfileScreen
import com.alithya.profile.presentation.ProfileViewModel
import com.alithya.settings.presentation.SettingScreen
import com.alithya.settings.presentation.SettingViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScreen(
    rootNavController : NavHostController,
    navController : NavHostController = rememberAnimatedNavController(),
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    settingViewModel: SettingViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel(),
) {

    val menus = listOf(
        BottomMenu.Home,
        BottomMenu.Search,
        BottomMenu.Profile
    )

    Scaffold(
        content = {
            if(windowSize == WindowWidthSizeClass.Expanded) {
                Row(modifier = Modifier.padding(it)) {
                    NavigationRails(
                        modifier = Modifier.padding(top = 16.dp),
                        navController = navController,
                        menus = menus
                    )
                    Content(
                        rootNavController = rootNavController,
                        navController = navController,
                        windowSize = windowSize,
                        profileViewModel = profileViewModel,
                        settingViewModel = settingViewModel,
                    )
                }
            }
            else {
                Content(
                    rootNavController = rootNavController,
                    navController = navController,
                    padding = it,
                    windowSize = windowSize,
                    profileViewModel = profileViewModel,
                    settingViewModel = settingViewModel,
                )
            }
        },
        bottomBar = {
            if(windowSize != WindowWidthSizeClass.Expanded) {
                BottomBarNavigation(
                    navController = navController,
                    menus = menus,
                )
            }
        },
    )
}

@Composable
fun Content(
    rootNavController : NavHostController,
    navController : NavHostController,
    padding: PaddingValues = PaddingValues(0.dp),
    windowSize: WindowWidthSizeClass,
    settingViewModel: SettingViewModel,
    profileViewModel: ProfileViewModel,
) {

    val onBackPressed = {
        navController.popBackStack()
    }

    val onNavigateToRouteScreen = { route : String ->
        navController.navigate(route)
    }

    val onNavigateToLoginScreen = {
        rootNavController.popBackStack()
        rootNavController.navigate(ScreenTarget.Login.route)
    }

    Box(
        modifier = Modifier
            .padding(padding)
            .background(MaterialTheme.colorScheme.scrim),
        contentAlignment = Alignment.Center
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = ScreenTarget.Home.route,
            enterTransition = {
                fadeIn(animationSpec = tween(FADE_DURATION_DEFAULT))
            },
            exitTransition ={
                fadeOut(animationSpec = tween(FADE_DURATION_DEFAULT))
            }
        ) {
            composable(ScreenTarget.Home.route) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Home Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(ScreenTarget.Search.route) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Search Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(ScreenTarget.Profile.route) {
                ProfileScreen(
                    windowSize = windowSize,
                    viewModel = profileViewModel,
                    onNavigateToRoute = onNavigateToRouteScreen,
                    onLogoutPressed = onNavigateToLoginScreen
                )
            }
            composable(ScreenTarget.AccountInformation.route) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Account Information Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(ScreenTarget.ChangePassword.route) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Change Password Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(ScreenTarget.WorkSkills.route) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Work Skills Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(ScreenTarget.Settings.route) {
                SettingScreen(
                    windowSize = windowSize,
                    viewModel = settingViewModel,
                    onBackPressed = onBackPressed,
                )
            }
            composable(ScreenTarget.Error.route){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Error Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            composable(
                route = ScreenTarget.DetailsFormation.route,
                arguments = listOf(navArgument(UUID_ARGUMENT_KEY) { type = NavType.StringType }),
                enterTransition = {
                    expandIn(
                        expandFrom = Alignment.CenterStart,
                        animationSpec = tween(EXPAND_DURATION)
                    ) + fadeIn(animationSpec = tween(FADE_DURATION_LONG))
                }
            ){ backStackEntry ->
                val formationUuid = backStackEntry.arguments?.getString(UUID_ARGUMENT_KEY)!!
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.scrim),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Details Formation Screen",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBarNavigation(
    menus : List<BottomMenu>,
    navController: NavHostController,
){
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = MaterialTheme.dimensions.default
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        menus.forEach { menu ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.testTag(menu.route),
                        imageVector = menu.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = menu.resourceId),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                selected = currentDestination?.route == menu.route,
                onClick = {
                    navController.navigate(menu.route){
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }
    }
}

@Composable
fun NavigationRails(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    menus : List<BottomMenu>
) {
    NavigationRail(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        menus.forEach { menu ->
            NavigationRailItem(
                modifier = Modifier.padding(bottom = MaterialTheme.dimensions.xlarge),
                icon = {
                    Icon(
                        imageVector = menu.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = menu.resourceId),
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                selected =  currentDestination?.route == menu.route,
                onClick = {
                    navController.navigate(menu.route){
                        launchSingleTop = true
                    }
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    EasyChatTheme {
        AppScreen(
            rootNavController = rememberNavController(),
            navController = rememberNavController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AppScreenDarkPreview() {
    EasyChatTheme {
        AppScreen(
            rootNavController = rememberNavController(),
            navController = rememberNavController(),
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, device = Devices.PIXEL_C)
@Composable
fun AppScreenLargeDarkPreview() {
    EasyChatTheme {
        AppScreen(
            rootNavController = rememberNavController(),
            navController = rememberNavController(),
            windowSize = WindowWidthSizeClass.Expanded,
        )
    }
}