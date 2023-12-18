package ca.josuelubaki.easychat

import com.alithya.common.utils.Constants.USECASES_REGEX
import com.alithya.common.utils.Constants.USECASE_REGEX
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.packages
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import org.junit.Test

/**
 * created by Josue Lubaki
 * date : 2023-09-14
 * version : 1.0.0
 */

class KonsistTest {

    private val featureLoginScope = Konsist.scopeFromModule("features/login")
    private val featureRegisterScope = Konsist.scopeFromModule("features/register")
    private val featureForgotPasswordScope = Konsist.scopeFromModule("features/forgot_password")
    private val featureProfileScope = Konsist.scopeFromModule("features/profile")

    private val refactoredModules =
        featureLoginScope +
        featureRegisterScope +
        featureForgotPasswordScope +
        featureProfileScope

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain', 'usecase' packages`() {
        refactoredModules
            .classes()
            .withNameEndingWith("UseCase")
            .assert { it.resideInPackage("..domain.usecases..") }
    }

    @Test
    fun `all classes in the 'usecases' package must ending with UseCase`(){
        refactoredModules
            .packages
            .filter { it.name.endsWith(suffix = "usecases", ignoreCase = false) } // Filter packages ending with "usecases"
            .assert {
                it.containingFile.hasNameMatching(Regex(USECASE_REGEX)) ||
                it.containingFile.hasNameMatching(Regex(USECASES_REGEX))
            }
    }

    @Test
    fun `all classes in the 'usecases' package, ending with 'UseCase', must have the 'invoke' function`() {
        refactoredModules
            .packages
            .filter { it.name.endsWith("usecases", ignoreCase = false) }
            .flatMap {
                it.containingFile
                    .classes()
                    .filter {
                            className ->
                        className.hasNameEndingWith("UseCase")
                    }
            }
            .assert {
                val hasSingleInvokeMethod = it.containsFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier
                }

                val hasSinglePublicDeclaration = it.numPublicOrDefaultDeclarations() == 1

                hasSingleInvokeMethod && hasSinglePublicDeclaration
            }
    }
}