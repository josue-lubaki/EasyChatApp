package com.alithya.setup

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.types.file
import java.io.File

/**
 * created by Josue Lubaki
 * date : 2023-10-25
 * version : 1.0.0
 */

class CLI : CliktCommand(){

    private val actualPackageName = "com.alithya.boilerplate"
    private val rootProjectName = "BoilerPlate"

    private val kotlinOldAndroidApp by argument().file().default(File("app/src/main/java/com/alithya/boilerplate"))
    private val kotlinAndroidTestApp by argument().file().default(File("app/src/androidTest/java/com/alithya/boilerplate"))
    private val kotlinTestApp by argument().file().default(File("app/src/test/java/com/alithya/boilerplate"))
    private val settingsGradle by argument().file().default(File("settings.gradle.kts"))

    private val appName : String by option().prompt("Enter the name of your app")
    private val packageName : String by option().prompt("Enter the package name of your app (com.example.app)")

    private val appModule by argument().file().default(File("app"))
    private val androidAppBuildGradle by argument().file().default(File("app/build.gradle.kts"))
    override fun run() {
        val androidAppBuildGradleContent = androidAppBuildGradle.readText()
        androidAppBuildGradle.delete()
        androidAppBuildGradle.createNewFile()
        androidAppBuildGradle.writeText(androidAppBuildGradleContent.replace(actualPackageName, packageName))

        echo("Processing...")

        setupAppName()
        setupAndroidApp(packageName)
        setupAndroidTestApp(packageName)
        setupTestApp(packageName)
        setupSettingsGradle(appName)

        GitAddCommitCommand("test commit message, setup app").main(emptyArray())
    }

    private fun setupSettingsGradle(appName: String) {
        val settingsGradleContent = settingsGradle.readText()
        settingsGradle.delete()
        settingsGradle.createNewFile()
        settingsGradle.writeText(settingsGradleContent.replace(rootProjectName, appName))
    }

    private fun setupTestApp(packageName: String) {
        val kotlinNewTestApp = File("app/src/test/kotlin/${packageName.replace(".", "/")}")
        kotlinNewTestApp.mkdirs()
        kotlinTestApp.copyRecursively(kotlinNewTestApp, overwrite = true)
        kotlinTestApp.deleteRecursively()
        File("app/src/test/java").deleteRecursively()

        kotlinNewTestApp
            .walkTopDown()
            .filter { it.isFile }
            .filter { !it.name.endsWith(".png") }
            .filter { !it.name.endsWith(".webp") }
            .forEach {
                val content = it.readText()
                it.delete()
                it.createNewFile()
                it.writeText(content.replace(actualPackageName, packageName))
            }
    }

    private fun setupAndroidTestApp(packageName: String) {
        val kotlinNewAndroidTestApp = File("app/src/androidTest/kotlin/${packageName.replace(".", "/")}")
        kotlinNewAndroidTestApp.mkdirs()
        kotlinAndroidTestApp.copyRecursively(kotlinNewAndroidTestApp, overwrite = true)
        kotlinAndroidTestApp.deleteRecursively()
        File("app/src/androidTest/java").deleteRecursively()

        kotlinNewAndroidTestApp
            .walkTopDown()
            .filter { it.isFile }
            .filter { !it.name.endsWith(".png") }
            .filter { !it.name.endsWith(".webp") }
            .forEach {
                val content = it.readText()
                it.delete()
                it.createNewFile()
                it.writeText(content.replace(actualPackageName, packageName))
            }
    }

    private fun setupAndroidApp(packageName: String) {
        val manifest = File("app/src/main/AndroidManifest.xml")
        val manifestContent = manifest.readText()
        manifest.delete()
        manifest.createNewFile()
        manifest.writeText(manifestContent.replace(actualPackageName, packageName))

        val kotlinNewAndroidApp = File("app/src/main/kotlin/${packageName.replace(".", "/")}")
        kotlinNewAndroidApp.mkdirs()
        kotlinOldAndroidApp.copyRecursively(kotlinNewAndroidApp, overwrite = true)
        kotlinOldAndroidApp.deleteRecursively()
        File("app/src/main/java").deleteRecursively()

        kotlinNewAndroidApp
            .walkTopDown()
            .filter { it.isFile }
            .filter { !it.name.endsWith(".png") }
            .filter { !it.name.endsWith(".webp") }
            .forEach {
                val content = it.readText()
                it.delete()
                it.createNewFile()
                it.writeText(content.replace(actualPackageName, packageName))
            }
    }

    private fun setupAppName() {
        val appNameToChange = "Boilerplate"

        // don't touch .png and .webp files
        val res = File("app/src/main/res")
        res.walkTopDown()
            .filter { it.isFile }
            .filter { !it.name.endsWith(".png") }
            .filter { !it.name.endsWith(".webp") }
            .forEach {
                val content = it.readText()
                it.delete()
                it.createNewFile()
                it.writeText(content.replace(appNameToChange, appName))
            }

        val stringsXml = File("app/src/main/res/values/strings.xml")
        val stringsXmlContent = stringsXml.readText()
        stringsXml.delete()
        stringsXml.createNewFile()
        stringsXml.writeText(stringsXmlContent.replace(appNameToChange, appName))

        val settingsGradle = File("settings.gradle.kts")
        val settingsGradleContent = settingsGradle.readText()
        settingsGradle.delete()
        settingsGradle.createNewFile()
        settingsGradle.writeText(settingsGradleContent.replace(appNameToChange, appName))
    }
}

class GitAddCommitCommand(private val message: String) : CliktCommand() {
    override fun run() {
        val gitAdd = ProcessBuilder("git", "add", ".")
        val gitCommit = ProcessBuilder("git", "commit", "-m", message)

        val gitAddProcess = gitAdd
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        val addExitCode = gitAddProcess.waitFor()
        if (addExitCode != 0) {
            echo(message = "git add failed with exit code : $addExitCode")
            return
        }

        echo(message = "git add success")

//        val gitCommitProcess = gitCommit
//            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//            .redirectError(ProcessBuilder.Redirect.INHERIT)
//            .start()
//
//        val commitExitCode = gitCommitProcess.waitFor()
//        if (commitExitCode != 0) {
//            echo(message = "git commit failed with exit code : $commitExitCode")
//            return
//        }

    }
}