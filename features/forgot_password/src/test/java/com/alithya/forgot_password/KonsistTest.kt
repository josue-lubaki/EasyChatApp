package com.alithya.forgot_password

import com.alithya.common.utils.Constants
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
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
    @Test
    fun `architecture layers have dependencies correct`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                // Define layers
                val presentation = Layer(
                    name = "presentation",
                    definedBy = "com.alithya.forgot_password.presentation.."
                )
                val domain = Layer(
                    name = "domain",
                    definedBy = "com.alithya.forgot_password.domain.."
                )
                val data = Layer(
                    name = "data",
                    definedBy = "com.alithya.forgot_password.data.."
                )

                // Define architecture assertions
                domain.dependsOnNothing()
                data.dependsOn(domain)
                presentation.dependsOn(domain)
            }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain', 'usecases' packages`() {
        Konsist.scopeFromProduction()
            .classes()
            .withNameEndingWith("UseCase")
            .assert { it.resideInPackage("..domain.usecases..") }
    }

    @Test
    fun `all classes in the 'usecases' package must ending with UseCase`(){
        Konsist
            .scopeFromProject()
            .packages
            .filter { it.name.endsWith(suffix = "usecases", ignoreCase = false) } // Filter packages ending with "usecases"
            .assert {
                it.containingFile.hasNameMatching(Regex(Constants.USECASE_REGEX)) ||
                        it.containingFile.hasNameMatching(Regex(Constants.USECASES_REGEX))
            }
    }

    @Test
    fun `all classes in the 'usecases' package, ending with 'UseCase', must have the 'invoke' function`() {
        Konsist
            .scopeFromProject()
            .packages
            .filter { it.name.endsWith("usecases") }
            .flatMap {
                it.containingFile
                    .classes()
                    .filter {
                        className ->
                        className.name.matches(Regex(Constants.USECASE_REGEX))
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