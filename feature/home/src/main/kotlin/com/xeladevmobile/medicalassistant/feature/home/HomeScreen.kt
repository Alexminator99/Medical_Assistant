package com.xeladevmobile.medicalassistant.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.xeladevmobile.medicalassistant.core.designsystem.theme.MedicalTheme
import com.xeladevmobile.medicalassistant.core.model.data.PatientStatistics
import com.xeladevmobile.medicalassistant.core.model.data.UserData
import com.xeladevmobile.medicalassistant.core.model.data.audiosPreview
import com.xeladevmobile.medicalassistant.core.model.data.calculateStatistics
import com.xeladevmobile.medicalassistant.core.model.data.patientUserData
import com.xeladevmobile.medicalassistant.core.ui.DevicePreviews

@Composable
internal fun HomeScreenRoute(
    onDashboardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        onDashboardClick = onDashboardClick,
        modifier = modifier,
        uiState = uiState,
    )
}

@Composable
internal fun HomeScreen(
    onDashboardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
) {
    when (uiState) {
        HomeUiState.Loading -> {
            InitRecordCard(
                userData = null,
                image = "",
            )
        }

        is HomeUiState.Success -> {
            HomeScreenContent(
                modifier = modifier,
                patientStatisticsList = uiState.audioRecords.calculateStatistics(),
                userData = uiState.userData,
            )
        }
    }
}

@DevicePreviews
@Composable
internal fun HomeScreenPreview() {
    MedicalTheme {
        HomeScreen(
            onDashboardClick = {},
            modifier = Modifier,
            uiState = HomeUiState.Success(
                userData = patientUserData,
                audioRecords = listOf(),
            ),
        )
    }
}

@Composable
internal fun HomeScreenContent(
    modifier: Modifier = Modifier,
    patientStatisticsList: List<PatientStatistics> = listOf(),
    userData: UserData,
) {
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 24.dp,
            modifier = modifier
                .testTag("home:feed"),
        ) {
            item {
                InitRecordCard(
                    userData, "",
                )
            }
            items(
                items = patientStatisticsList,
                key = { it.header },
            ) { statistics ->
                // Animate the card visibility
                AnimatedVisibility(
                    visible = true, // Control this state as needed
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    StatisticsCard(statistics)
                }
            }
        }
    }
}

@Composable
internal fun InitRecordCard(userData: UserData?, image: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    val username = if (userData == null)
                        ". We are loading your data."
                    else " ${userData.name}."
                    Text(
                        text = "Welcome to Medical Assistant$username",
                        style =
                        typography.headlineMedium,
                    )
                    Text(
                        text = "Start recording your audio", style = typography.bodyMedium,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                }
            }

            Text(
                text = "Press the button below to start recording. All records are completely private and will be stored in your device.",
                style = typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
            )

            Row {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = image).apply(
                            block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                transformations(CircleCropTransformation())
                            },
                        ).build(),
                        placeholder = painterResource(id = R.drawable.ic_person_placeholder),
                        error = painterResource(id = R.drawable.ic_person_placeholder),
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape),
                )

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically),
                ) {
                    Text(text = "Start recording")
                }
            }
        }
    }
}

@Preview
@Composable
internal fun InitRecordCardPreview() {
    MedicalTheme {
        InitRecordCard(
            patientUserData, "",
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun HomeScreenContentPreview() {
    MedicalTheme {
        HomeScreenContent(
            modifier = Modifier,
            patientStatisticsList = audiosPreview.calculateStatistics(),
            userData = patientUserData,
        )
    }
}

@Composable
internal fun StatisticsCard(statistics: PatientStatistics) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Add vertical padding for the card
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = statistics.header,
                        style = typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                    Text(
                        text = statistics.description,
                        style = typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                // Display the statistics value with bullet points and bold text, including colons
                val annotatedString = buildAnnotatedString {
                    withStyle(style = ParagraphStyle(lineHeight = 20.sp)) {
                        statistics.value.split("\n").forEach { line ->
                            if (line.startsWith("•")) {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(line.substringBefore(": ") + ":")
                                }
                                append(" " + line.substringAfter(": ") + "\n")
                            } else {
                                append("$line\n")
                            }
                        }
                    }
                }

                Text(
                    text = annotatedString,
                    style = typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                )

                Text(
                    text = "Updated at ${statistics.updatedAt}",
                    style = typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}


@Composable
fun BulletPointText(line: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("•", style = typography.bodyMedium)
        Text(
            text = line.removePrefix("• "),
            style = typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@DevicePreviews
@Composable
internal fun StatisticsCardPreview() {
    MedicalTheme {
        StatisticsCard(
            statistics = PatientStatistics(
                header = "Statistics",
                description = "Statistics description with a very large text to check if the text is wrapped correctly",
                value = "A really large and complex explication about the statistics",
                updatedAt = "2021-09-01",
            ),
        )
    }
}
