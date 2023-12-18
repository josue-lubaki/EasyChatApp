pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

//======== enable feature flags ==================

// Allow to use "implementation(project.common)" instead of "implementation(project(":common"))
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "EasyChat"
include(":app")
include(":common")
include(":features:login")
include(":features:register")
include(":features:forgot_password")
include(":features:profile")
include(":features:settings")
include(":setup")
include(":features:chat")
