plugins {
    alias(libs.plugins.medicalassist.android.feature)
    alias(libs.plugins.medicalassist.android.library.compose)
    alias(libs.plugins.medicalassist.android.library.jacoco)
}

android {
    namespace = "com.xeladevmobile.medicalassistant.feature.voice"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.permissions)
}