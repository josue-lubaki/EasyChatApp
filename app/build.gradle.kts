@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.serializationPlugin)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "ca.josuelubaki.easychat"
    compileSdk = 34

    defaultConfig {
        applicationId = "ca.josuelubaki.easychat"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "ca.josuelubaki.easychat.CustomTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.common)
    implementation(projects.features.login)
    implementation(projects.features.loginatp)
    implementation(projects.features.register)
    implementation(projects.features.forgotPassword)
    implementation(projects.features.profile)
    implementation(projects.features.settings)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    // compose
    implementation(platform(libs.compose.bom))
    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.window.size)
    implementation(libs.material.icons.extended)

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

    // accompanist
    implementation(libs.accompanist.navigation.animation)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.konsist)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.navigation.test)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Hilt testing dependency
    androidTestImplementation(libs.hilt.android.testing)
    // Make Hilt generate code in the androidTest folder
    kaptAndroidTest(libs.hilt.android.compiler)
}