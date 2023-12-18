package com.alithya.features.register

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.alithya.common.di.dataStoreModule
import com.alithya.common.di.networkModule
import com.alithya.common.di.userModule
import com.alithya.common.ui.themes.EasyChatTheme
import com.alithya.features.register.di.registerModule
import com.alithya.features.register.presentation.RegisterScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    private val contextModule = module {
        single<Context> { InstrumentationRegistry.getInstrumentation().context }
    }

    private val testModules = listOf(
        contextModule,
        registerModule,
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

            EasyChatTheme {
                RegisterScreen(
                    navController = navController,
                )
            }
        }
    }

    @Test
    fun shouldDisplayAllText() {
        composeTestRule.onNodeWithTag("registerTitleQa").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerFirstNameQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerFirstNameQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerLastNameQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerLastNameQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerEmailQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerEmailQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerPasswordQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerPasswordQa-input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerPasswordQa-IconButton").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasMinLength").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasMinLength-CloseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasUppercase").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasUppercase-CloseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasLowercase").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasLowercase-CloseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasDigits").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasDigits-CloseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasSymbol").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("PasswordCheckerQa-hasSymbol-CloseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerConfirmPasswordQa-title").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerConfirmPasswordQa-input").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("registerConfirmPasswordQa-IconButton").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonPrimary-createAccountQa").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonSecondary-LoginButtonQa").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun shouldDisplayCorrectTextForTheLanguage() {
        val currentLocale = Resources.getSystem().configuration.locales.get(0)
        val isLandScape =  Resources.getSystem().configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        when (currentLocale.language) {
            "fr" -> {

                if(!isLandScape){
                    composeTestRule.onNodeWithTag("registerTitleQa").assertTextContains(("Créez\nvotre compte"))
                } else {
                    composeTestRule.onNodeWithTag("registerTitleQa").assertTextContains(("Créez votre compte"))
                }

                composeTestRule.onNodeWithTag("registerFirstNameQa-title").assertTextContains("Prénom")
                composeTestRule.onNodeWithTag("registerFirstNameQa-input").assertTextContains("Prénom")
                composeTestRule.onNodeWithTag("registerLastNameQa-title").assertTextContains("Nom")
                composeTestRule.onNodeWithTag("registerLastNameQa-input").assertTextContains("Nom")
                composeTestRule.onNodeWithTag("registerEmailQa-title").assertTextContains("Courriel")
                composeTestRule.onNodeWithTag("registerEmailQa-input").assertTextContains("prenom.nom@alithya.com")
                composeTestRule.onNodeWithTag("registerPasswordQa-title").assertTextContains("Mot de passe")
                composeTestRule.onNodeWithTag("registerPasswordQa-input").assertTextContains("••••••")
                composeTestRule.onNodeWithTag("registerConfirmPasswordQa-title").assertTextContains("Confirmer le mot de passe")
                composeTestRule.onNodeWithTag("registerConfirmPasswordQa-input").assertTextContains("Confirmer le mot de passe")
                composeTestRule.onNodeWithTag("ButtonPrimary-createAccountQa-text", useUnmergedTree = true).assertTextContains("CRÉER UN COMPTE")
                composeTestRule.onNodeWithTag("ButtonSecondary-LoginButtonQa-text", useUnmergedTree = true).assertTextContains("SE CONNECTER")
                composeTestRule.onNodeWithTag("registerAlreadyHaveAnAccountQa").assertTextContains("Vous avez déjà un compte ?")
            }
            else -> {

                if(!isLandScape){
                    composeTestRule.onNodeWithTag("registerTitleQa").assertTextContains("Create\nyour account")
                } else {
                    composeTestRule.onNodeWithTag("registerTitleQa").assertTextContains("Create your account")
                }

                composeTestRule.onNodeWithTag("registerFirstNameQa-title").assertTextContains("First name")
                composeTestRule.onNodeWithTag("registerFirstNameQa-input").assertTextContains("First name")
                composeTestRule.onNodeWithTag("registerLastNameQa-title").assertTextContains("Last name")
                composeTestRule.onNodeWithTag("registerLastNameQa-input").assertTextContains("Last name")
                composeTestRule.onNodeWithTag("registerEmailQa-title").assertTextContains("Email")
                composeTestRule.onNodeWithTag("registerEmailQa-input").assertTextContains("firstname.lastname@alithya.com")
                composeTestRule.onNodeWithTag("registerPasswordQa-title").assertTextContains("Password")
                composeTestRule.onNodeWithTag("registerPasswordQa-input").assertTextContains("••••••")
                composeTestRule.onNodeWithTag("registerConfirmPasswordQa-title").assertTextContains("Confirm password")
                composeTestRule.onNodeWithTag("registerConfirmPasswordQa-input").assertTextContains("Confirm password")
                composeTestRule.onNodeWithTag("ButtonPrimary-createAccountQa-text", useUnmergedTree = true).assertTextContains("CREATE ACCOUNT")
                composeTestRule.onNodeWithTag("ButtonSecondary-LoginButtonQa-text", useUnmergedTree = true).assertTextContains("LOGIN")
                composeTestRule.onNodeWithTag("registerAlreadyHaveAnAccountQa").assertTextContains("Already have an account ?")
            }
        }
    }
}