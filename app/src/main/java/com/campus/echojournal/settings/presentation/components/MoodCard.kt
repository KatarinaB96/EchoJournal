package com.campus.echojournal.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.settings.presentation.SettingsAction
import com.campus.echojournal.settings.presentation.SettingsState
import com.campus.echojournal.ui.theme.OnSurface
import com.campus.echojournal.ui.theme.OnSurfaceVariant

@Composable
fun MoodCard(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    Card(
        modifier = Modifier
            .height(148.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                bottom = 12.dp,
                top = 14.dp,
                start = 14.dp,
                end = 14.dp
            )
        ) {
            Text(
                stringResource(R.string.my_mood), color = OnSurface,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(2.dp))
            Text(
                stringResource(R.string.select_default_mood),
                color = OnSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(14.dp))
            SelectableMood(
                savedMoodIndex = state.savedMoodIndex,
                onMoodSelected = { index ->
                    onAction(SettingsAction.OnMoodIndexChanged(index))
                })
        }
    }
}

@Composable
fun SelectableMood(
    savedMoodIndex: Int,
    onMoodSelected: (Int) -> Unit
) {

    val moods = listOf(
        Pair(R.drawable.mood_stresses_active_off to R.drawable.mood_stresses_active, "Stressed"),
        Pair(R.drawable.mood_sad_active_off to R.drawable.mood_sad_active_on, "Sad"),
        Pair(R.drawable.mood_neutral_active_off to R.drawable.mood_neutral_active_on, "Neutral"),
        Pair(R.drawable.mood_peaceful_active_off to R.drawable.mood_peaceful_active_on, "Peaceful"),
        Pair(R.drawable.mood_excited_active_off to R.drawable.mood_excited_active_on, "Excited")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        moods.forEachIndexed { index, (images, moodName) ->
            val (emptyImage, filledImage) = images

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        onMoodSelected(index)
                    }
            ) {
                Image(
                    painter = painterResource(
                        id = if (index == savedMoodIndex) filledImage else emptyImage
                    ),
                    contentDescription = "Mood $moodName",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = moodName,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (index == savedMoodIndex) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview
@Composable
fun MoodCardPreview() {
    MoodCard(SettingsState(), {})
}