package com.xeladevmobile.medicalassistant.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.xeladevmobile.medicalassistant.R
import com.xeladevmobile.medicalassistant.core.data.util.NetworkMonitor
import com.xeladevmobile.medicalassistant.core.designsystem.component.*
import com.xeladevmobile.medicalassistant.core.designsystem.icon.MedicalIcons
import com.xeladevmobile.medicalassistant.core.designsystem.theme.GradientColors
import com.xeladevmobile.medicalassistant.core.designsystem.theme.LocalGradientColors
import com.xeladevmobile.medicalassistant.core.model.data.UserData
import com.xeladevmobile.medicalassistant.core.model.data.isLoggedIn
import com.xeladevmobile.medicalassistant.feature.settings.SettingsDialog
import com.xeladevmobile.medicalassistant.navigation.MedicalNavHost
import com.xeladevmobile.medicalassistant.navigation.TopLevelDestination
import com.xeladevmobile.medicalassistant.feature.settings.R as settingsR

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun MedicalApp(
    userData: UserData,
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    appState: MedicalAppState = rememberMedicalAppState(
        networkMonitor = networkMonitor,
        windowSizeClass = windowSizeClass,
    ),
) {
    val shouldShowGradientBackground =
        appState.currentTopLevelDestination == TopLevelDestination.HOME
    var showSettingsDialog by rememberSaveable {
        mutableStateOf(false)
    }

    MedicalBackground {
        MedicalGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(R.string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                )
            }

            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (appState.shouldShowBottomBar && userData.isLoggedIn()) {
                        MedicalBottomBar(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("MedicalBottomBar"),
                        )
                    }
                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    if (appState.shouldShowNavRail && userData.isLoggedIn()) {
                        MedicalNavRail(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier
                                .testTag("MedicalNavRail")
                                .safeDrawingPadding(),
                        )
                    }

                    Column(Modifier.fillMaxSize()) {
                        // Show the top app bar on top level destinations.
                        val destination = appState.currentTopLevelDestination
                        if (destination != null) {
                            MedicalTopAppBar(
                                titleRes = destination.titleTextId,
                                navigationIcon = MedicalIcons.Search,
                                navigationIconContentDescription = stringResource(
                                    id = settingsR.string.top_app_bar_navigation_icon_description,
                                ),
                                actionIcon = MedicalIcons.Settings,
                                actionIconContentDescription = stringResource(
                                    id = settingsR.string.top_app_bar_action_icon_description,
                                ),
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = Color.Transparent,
                                ),
                                onActionClick = { showSettingsDialog = true },
                                onNavigationClick = { appState.navigateToSearch() },
                            )
                        }

                        MedicalNavHost(
                            userData = userData,
                            appState = appState,
                            onShowSnackbar = { message, action ->
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                    duration = Short,
                                ) == ActionPerformed
                            },
                        )
                    }

                    // TODO: We may want to add padding or spacer when the snackbar is shown so that
                    //  content doesn't display behind it.
                }
            }
        }
    }
}

@Composable
private fun MedicalNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MedicalNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MedicalNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun MedicalBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    MedicalNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            MedicalNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
