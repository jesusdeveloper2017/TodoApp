// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    //Plugin de compose
    alias(libs.plugins.compose.compiler) apply false

    //Room
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
}