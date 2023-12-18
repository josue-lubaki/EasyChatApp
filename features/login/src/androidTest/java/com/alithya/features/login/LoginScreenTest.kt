package com.alithya.features.login

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alithya.common.di.dataStoreModule
import com.alithya.common.di.networkModule
import com.alithya.common.di.userModule
import com.alithya.common.ui.themes.BoilerPlateTheme
import com.alithya.features.login.di.loginModule
import com.alithya.features.login.presentation.LoginScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    private val contextModule = module {
        single<Context> { InstrumentationRegistry.getInstrumentation().context }
    }

    private val testModules = listOf(
        contextModule,
        loginModule,
        networkModule,
        dataStoreModule,
        userModule
    )

    @Before
    fun setUp() {
        loadKoinModules(modules = testModules)

        composeTestRule.setContent {

            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            BoilerPlateTheme {
                LoginScreen(
                    navController = navController,
                )
            }
        }
    }

    @Test
    fun shouldDisplayAllText() {

        composeTestRule.onNodeWithTag("loginTitleQa").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginEmailQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginEmailQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginPasswordQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginPasswordQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonPrimary-loginSignInButtonQa").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonSecondary-loginRegisterButtonQa").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginForgotPassword").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginRegisterQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loginPasswordQa-IconButton").assertIsDisplayed()
    }

    @Test
    fun shouldDisplayCorrectTextForTheLanguage(){
        val currentLocale = Resources.getSystem().configuration.locales.get(0)

        composeTestRule.onNodeWithTag("loginEmailQa-input").assertTextContains("name.lastname@alithya.com")
        composeTestRule.onNodeWithTag("loginPasswordQa-input").assertTextContains("••••••")

        when (currentLocale.language) {
            "fr" -> {
                composeTestRule.onNodeWithTag("loginTitleQa").assertTextContains("Se connecter")
                composeTestRule.onNodeWithTag("ButtonPrimary-loginSignInButtonQa").assertTextContains("SE CONNECTER")
                composeTestRule.onNodeWithTag("ButtonSecondary-loginRegisterButtonQa").assertTextContains("S'INSCRIRE")
                composeTestRule.onNodeWithTag("loginForgotPassword").assertTextContains("Mot de passe oublié?")
                composeTestRule.onNodeWithTag("loginRegisterQa-title").assertTextContains("Vous n'avez pas encore de compte ?")
            }
            else -> {
                composeTestRule.onNodeWithTag("loginTitleQa").assertTextContains("Log In")
                composeTestRule.onNodeWithTag("ButtonPrimary-loginSignInButtonQa").assertTextContains("LOGIN")
                composeTestRule.onNodeWithTag("ButtonSecondary-loginRegisterButtonQa").assertTextContains("REGISTER")
                composeTestRule.onNodeWithTag("loginForgotPassword").assertTextContains("Forgot password?")
                composeTestRule.onNodeWithTag("loginRegisterQa-title").assertTextContains("You don't have an account yet ?")
            }
        }
    }

    @Test
    fun shouldBeAbleToWriteEmailAndPassword() {
        composeTestRule.onNodeWithTag("loginEmailQa-input").performTextInput("toto.toto@alithya.com")
        composeTestRule.onNodeWithTag("loginPasswordQa-input").performTextInput("12345678")

        composeTestRule.onNodeWithTag("loginEmailQa-input").assertTextContains("toto.toto@alithya.com")
        composeTestRule.onNodeWithTag("loginPasswordQa-input").assertTextContains("••••••••")

        composeTestRule.onNodeWithTag("loginPasswordQa-IconButton").performClick()
        composeTestRule.onNodeWithTag("loginPasswordQa-input").assertTextContains("12345678")

        composeTestRule.onNodeWithTag("loginPasswordQa-IconButton").performClick()
        composeTestRule.onNodeWithTag("loginPasswordQa-input").assertTextContains("••••••••")
    }
}