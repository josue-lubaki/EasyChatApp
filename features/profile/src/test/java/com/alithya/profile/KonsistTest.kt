package com.alithya.profile

import com.alithya.common.utils.Constants
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
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
                    definedBy = "com.alithya.profile.presentation.."
                )

                // Define architecture assertions
                presentation.dependsOnNothing()
            }
    }
}