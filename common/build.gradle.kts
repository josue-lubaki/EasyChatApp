@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.serializationPlugin)
    id("kotlin-parcelize")
}

android {
    namespace = "com.alithya.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        buildConfigField("String", "BASE_URL", "\"http://drupal.staging.testnv.ca\"")
        buildConfigField("String", "CLIENT_ID", "\"607f1517-77f6-431e-929f-198a2675d3b2\"")
        buildConfigField("String", "CLIENT_SECRET", "\"dev123\"")
        buildConfigField("String", "ADMIN_USERNAME", "\"francis.paquin-brien@alithya.com\"")
        buildConfigField("String", "ADMIN_PASSWORD", "\"admin@Alithya12\"")
        buildConfigField("String", "GRANT_TYPE", "\"password\"")

        testInstrumentationRunner = "com.alithya.common.CustomTestRunner"
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

    implementation(libs.core.ktx)
    implementation(libs.appcompat)

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

    // datastore
    implementation(libs.datastore)

    // coil
    implementation(libs.coil)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.konsist)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.navigation.test)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}