package com.xeladevmobile.medicalassistant.feature.records

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.xeladevmobile.medicalassistant.core.formatCreatedDate
import com.xeladevmobile.medicalassistant.core.formatDuration
import com.xeladevmobile.medicalassistant.core.model.data.Audio
import com.xeladevmobile.medicalassistant.core.model.data.Emotion
import com.xeladevmobile.medicalassistant.core.model.data.audiosPreview

@Composable
fun AudioRecordItem(
    audio: Audio,
    emotion: Emotion, // Enum class representing the different emotions
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Define a color based on the emotion
    val backgroundColor = colorForEmotion(emotion)
    // Define an emoji based on the emotion
    val emoji = emojiForEmotion(emotion)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ), // Set the background color based on emotion
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = audio.path.substringAfterLast('/'),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Duration: ${formatDuration(audio.duration)}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Created: ${formatCreatedDate(audio.createdDate)}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            // Add animated emoji based on the emotion
            AnimatedEmoji(emoji = emoji)
        }
    }
}

// Helper function to get a color based on the emotion
fun colorForEmotion(emotion: Emotion): Color {
    return when (emotion) {
        Emotion.Neutral -> Color.Gray
        Emotion.Angry -> Color.Red
        Emotion.Happiness -> Color(0xFF968022) // A golden shade for better contrast
        Emotion.Disgust -> Color(0xFF4CAF50) // A darker green for better contrast
        Emotion.Fear -> Color.DarkGray
    }
}

// Helper function to get an emoji based on the emotion
fun emojiForEmotion(emotion: Emotion): String {
    return when (emotion) {
        Emotion.Neutral -> "😐"
        Emotion.Angry -> "😠"
        Emotion.Happiness -> "😄"
        Emotion.Disgust -> "🤢"
        Emotion.Fear -> "😨"
    }
}

@Composable
fun AnimatedEmoji(emoji: String) {
    val animated = remember { Animatable(initialValue = 0f) }

    // This will run the animation when this composable enters the Composition
    LaunchedEffect(true) {
        animated.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        )
    }

    Text(
        text = emoji,
        modifier = Modifier
            .graphicsLayer {
                val scale = lerp(0.8f, 1f, animated.value)
                scaleX = scale
                scaleY = scale
                alpha = animated.value
            },
        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp)
    )
}

@Preview
@Composable
fun AudioRecordItemPreview() {
    AudioRecordItem(
        audio = audiosPreview.first(),
        onClick = {},
        emotion = Emotion.Neutral,
    )
}
