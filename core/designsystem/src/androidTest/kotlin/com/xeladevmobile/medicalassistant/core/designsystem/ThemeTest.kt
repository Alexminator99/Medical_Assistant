

package com.xeladevmobile.medicalassistant.core.designsystem

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.xeladevmobile.medicalassistant.core.designsystem.theme.BackgroundTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.DarkAndroidBackgroundTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.DarkAndroidColorScheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.DarkAndroidGradientColors
import com.xeladevmobile.medicalassistant.core.designsystem.theme.DarkDefaultColorScheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.GradientColors
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LightAndroidBackgroundTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LightAndroidColorScheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LightAndroidGradientColors
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LightDefaultColorScheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LocalBackgroundTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LocalGradientColors
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LocalTintTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.MedicalTheme
import com.xeladevmobile.medicalassistant.core.designsystem.theme.TintTheme
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Tests [MedicalTheme] using different combinations of the theme mode parameters:
 * darkTheme, disableDynamicTheming, and androidTheme.
 *
 * It verifies that the various composition locals — [MaterialTheme], [LocalGradientColors] and
 * [LocalBackgroundTheme] — have the expected values for a given theme mode, as specified by the
 * design system.
 */
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun darkThemeFalse_dynamicColorFalse_androidThemeFalse() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = false,
                disableDynamicTheming = true,
                androidTheme = false,
            ) {
                val colorScheme = LightDefaultColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = defaultGradientColors(colorScheme)
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorFalse_androidThemeFalse() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = true,
                disableDynamicTheming = true,
                androidTheme = false,
            ) {
                val colorScheme = DarkDefaultColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = defaultGradientColors(colorScheme)
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeFalse_dynamicColorTrue_androidThemeFalse() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = false,
                disableDynamicTheming = false,
                androidTheme = false,
            ) {
                val colorScheme = dynamicLightColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = dynamicGradientColorsWithFallback(colorScheme)
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = dynamicTintThemeWithFallback(colorScheme)
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorTrue_androidThemeFalse() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = true,
                disableDynamicTheming = false,
                androidTheme = false,
            ) {
                val colorScheme = dynamicDarkColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = dynamicGradientColorsWithFallback(colorScheme)
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = defaultBackgroundTheme(colorScheme)
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = dynamicTintThemeWithFallback(colorScheme)
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeFalse_dynamicColorFalse_androidThemeTrue() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = false,
                disableDynamicTheming = true,
                androidTheme = true,
            ) {
                val colorScheme = LightAndroidColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = LightAndroidGradientColors
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = LightAndroidBackgroundTheme
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorFalse_androidThemeTrue() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = true,
                disableDynamicTheming = true,
                androidTheme = true,
            ) {
                val colorScheme = DarkAndroidColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = DarkAndroidGradientColors
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = DarkAndroidBackgroundTheme
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeFalse_dynamicColorTrue_androidThemeTrue() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = false,
                disableDynamicTheming = false,
                androidTheme = true,
            ) {
                val colorScheme = LightAndroidColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = LightAndroidGradientColors
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = LightAndroidBackgroundTheme
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Test
    fun darkThemeTrue_dynamicColorTrue_androidThemeTrue() {
        composeTestRule.setContent {
            MedicalTheme(
                darkTheme = true,
                disableDynamicTheming = false,
                androidTheme = true,
            ) {
                val colorScheme = DarkAndroidColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
                val gradientColors = DarkAndroidGradientColors
                assertEquals(gradientColors, LocalGradientColors.current)
                val backgroundTheme = DarkAndroidBackgroundTheme
                assertEquals(backgroundTheme, LocalBackgroundTheme.current)
                val tintTheme = defaultTintTheme()
                assertEquals(tintTheme, LocalTintTheme.current)
            }
        }
    }

    @Composable
    private fun dynamicLightColorSchemeWithFallback(): ColorScheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dynamicLightColorScheme(LocalContext.current)
        } else {
            LightDefaultColorScheme
        }
    }

    @Composable
    private fun dynamicDarkColorSchemeWithFallback(): ColorScheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            dynamicDarkColorScheme(LocalContext.current)
        } else {
            DarkDefaultColorScheme
        }
    }

    private fun emptyGradientColors(colorScheme: ColorScheme): GradientColors {
        return GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    }

    private fun defaultGradientColors(colorScheme: ColorScheme): GradientColors {
        return GradientColors(
            top = colorScheme.inverseOnSurface,
            bottom = colorScheme.primaryContainer,
            container = colorScheme.surface,
        )
    }

    private fun dynamicGradientColorsWithFallback(colorScheme: ColorScheme): GradientColors {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            emptyGradientColors(colorScheme)
        } else {
            defaultGradientColors(colorScheme)
        }
    }

    private fun defaultBackgroundTheme(colorScheme: ColorScheme): BackgroundTheme {
        return BackgroundTheme(
            color = colorScheme.surface,
            tonalElevation = 2.dp,
        )
    }

    private fun defaultTintTheme(): TintTheme {
        return TintTheme()
    }

    private fun dynamicTintThemeWithFallback(colorScheme: ColorScheme): TintTheme {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            TintTheme(colorScheme.primary)
        } else {
            TintTheme()
        }
    }

    /**
     * Workaround for the fact that the Medical design system specify all color scheme values.
     */
    private fun assertColorSchemesEqual(
        expectedColorScheme: ColorScheme,
        actualColorScheme: ColorScheme,
    ) {
        assertEquals(expectedColorScheme.primary, actualColorScheme.primary)
        assertEquals(expectedColorScheme.onPrimary, actualColorScheme.onPrimary)
        assertEquals(expectedColorScheme.primaryContainer, actualColorScheme.primaryContainer)
        assertEquals(expectedColorScheme.onPrimaryContainer, actualColorScheme.onPrimaryContainer)
        assertEquals(expectedColorScheme.secondary, actualColorScheme.secondary)
        assertEquals(expectedColorScheme.onSecondary, actualColorScheme.onSecondary)
        assertEquals(expectedColorScheme.secondaryContainer, actualColorScheme.secondaryContainer)
        assertEquals(
            expectedColorScheme.onSecondaryContainer,
            actualColorScheme.onSecondaryContainer,
        )
        assertEquals(expectedColorScheme.tertiary, actualColorScheme.tertiary)
        assertEquals(expectedColorScheme.onTertiary, actualColorScheme.onTertiary)
        assertEquals(expectedColorScheme.tertiaryContainer, actualColorScheme.tertiaryContainer)
        assertEquals(expectedColorScheme.onTertiaryContainer, actualColorScheme.onTertiaryContainer)
        assertEquals(expectedColorScheme.error, actualColorScheme.error)
        assertEquals(expectedColorScheme.onError, actualColorScheme.onError)
        assertEquals(expectedColorScheme.errorContainer, actualColorScheme.errorContainer)
        assertEquals(expectedColorScheme.onErrorContainer, actualColorScheme.onErrorContainer)
        assertEquals(expectedColorScheme.background, actualColorScheme.background)
        assertEquals(expectedColorScheme.onBackground, actualColorScheme.onBackground)
        assertEquals(expectedColorScheme.surface, actualColorScheme.surface)
        assertEquals(expectedColorScheme.onSurface, actualColorScheme.onSurface)
        assertEquals(expectedColorScheme.surfaceVariant, actualColorScheme.surfaceVariant)
        assertEquals(expectedColorScheme.onSurfaceVariant, actualColorScheme.onSurfaceVariant)
        assertEquals(expectedColorScheme.inverseSurface, actualColorScheme.inverseSurface)
        assertEquals(expectedColorScheme.inverseOnSurface, actualColorScheme.inverseOnSurface)
        assertEquals(expectedColorScheme.outline, actualColorScheme.outline)
    }
}