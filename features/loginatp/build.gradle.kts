plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "ca.josue_lubaki.loginatp"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "com.alithya.features.loginatp.CustomTestRunner"
        consumerProguardFiles("consumer-rules.pro")

        configurations.all {
            resolutionStrategy {
                force("androidx.emoji2:emoji2-views-helper:1.3.0")
                force("androidx.emoji2:emoji2:1.3.0")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    implementation(projects.common)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.window.size)
    implementation(libs.material.icons.extended)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // large screens
    implementation(libs.androidx.window)
    implementation(libs.accompanist.adaptive)

    // navigation compose
    implementation(libs.navigation.compose)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // okHttp logging interceptor
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.timber)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // coil
    implementation(libs.coil)

    // country code picker
//    implementation(libs.country.code.picker)

    implementation("androidx.compose.material:material:1.5.4")
    implementation("com.github.jump-sdk:jetpack_compose_country_code_picker_emoji:2.2.6")

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.konsist)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.navigation.test)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}