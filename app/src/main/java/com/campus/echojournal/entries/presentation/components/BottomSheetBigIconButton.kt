package com.campus.echojournal.entries.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.ui.theme.BlueGradient1
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2
import com.campus.echojournal.ui.theme.PrimaryContainer


@Composable
fun BottomSheetBigIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isRecording : Boolean,
    content: @Composable () -> Unit
) {

    val firstRingScale by animateFloatAsState(
        targetValue = if (isRecording) 2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "firstRing"
    )

    val secondRingScale by animateFloatAsState(
        targetValue = if (isRecording) 1.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "secondRing"
    )


    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (isRecording) {
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .scale(firstRingScale)
                    .background(
                        color = GradientColor2,
                        shape = CircleShape
                    )

            )
            Box(
                modifier = Modifier
                    .size(108.dp)
                    .scale(secondRingScale)
                    .background(
                        color = GradientColor1,
                        shape = CircleShape
                    )
            )
        }

        IconButton(
            onClick = {
                onClick()
            },
            modifier = modifier
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            BlueGradient1,
                            PrimaryContainer
                        )
                    )
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun BottomSheetBigIconButtonPreview() {
    EchoJournalTheme {
        BottomSheetBigIconButton(
            onClick = { },
            modifier = Modifier.size(48.dp),
            isRecording = false
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Check",
                tint = MaterialTheme.colorScheme.onPrimary

            )
        }
    }
}
