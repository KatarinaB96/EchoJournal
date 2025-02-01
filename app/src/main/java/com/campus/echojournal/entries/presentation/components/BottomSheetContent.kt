package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor2
import java.util.Locale

@Composable
fun BottomSheetContent(
    onCancelRecording: () -> Unit,
    onSaveRecording: () -> Unit,
    onPauseRecording: () -> Unit,
    onStartRecording: () -> Unit,
    onResumeRecording: () -> Unit,
    onCloseBottomSheet: () -> Unit,
    isRecording: Boolean,
    counter: Int,
    modifier: Modifier = Modifier,

) {

    LaunchedEffect(Unit) {
        onStartRecording()

    }

    val formattedTime = remember(counter) {
        val hours = counter / 3600
        val minutes = (counter % 3600) / 60
        val seconds = counter % 60
        String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                text = stringResource(R.string.recording_your_memories),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = formattedTime,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall


            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomSheetIconButton(
                onClick = {
                    onCancelRecording()
                    onCloseBottomSheet()
                },
                containerColor = MaterialTheme.colorScheme.errorContainer,
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Cancel Recording",
                        modifier = Modifier.size(14.dp)
                    )
                },
                size = 48.dp
            )
            BottomSheetBigIconButton(
                onClick = {
                    if (isRecording) {
                        onSaveRecording()
                        onCloseBottomSheet()

                    } else {
                        onResumeRecording()
                    }
                },
                isRecording = isRecording,
                modifier = Modifier.size(72.dp)
            ) {

                Icon(
                    painter = painterResource(id = if (isRecording) R.drawable.ic_check else R.drawable.ic_mic),
                    contentDescription = "Save Recording",
                    tint = Color.White,
                    modifier = Modifier.size(21.dp)
                )
            }
            BottomSheetIconButton(
                onClick = {
                    if (isRecording) {
                        onPauseRecording()
                    } else {
                        onSaveRecording()
                        onCloseBottomSheet()
                    }
                },
                containerColor = GradientColor2,
                icon = {
                    Image(
                        painter = painterResource(id = if (isRecording) R.drawable.ic_pause else R.drawable.ic_check),
                        contentDescription = "Pause Recording",
                        modifier = Modifier.size(14.dp)
                    )
                },
                size = 48.dp
            )
        }

    }
}

@Preview
@Composable
private fun BottomSheetContentPreview() {
    EchoJournalTheme {
        Surface {
            BottomSheetContent(
                isRecording = false,
                onCancelRecording = {},
                onSaveRecording = {},
                onPauseRecording = {},
                onStartRecording = {},
                onResumeRecording = {},
                onCloseBottomSheet = {},
                counter = 0,
                modifier = Modifier.height(360.dp)
            )
        }
    }
}