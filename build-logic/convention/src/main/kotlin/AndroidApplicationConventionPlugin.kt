import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.xeladevmobile.medicalassistant.configureGradleManagedDevices
import com.xeladevmobile.medicalassistant.configureKotlinAndroid
import com.xeladevmobile.medicalassistant.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("medicalassist.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                configureGradleManagedDevices(this)
            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }
        }
    }

}