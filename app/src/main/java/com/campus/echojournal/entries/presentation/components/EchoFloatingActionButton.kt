package com.campus.echojournal.entries.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.AnimationGradientColor1
import com.campus.echojournal.ui.theme.AnimationGradientColor2
import com.campus.echojournal.ui.theme.BlueGradient1
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.PrimaryContainer
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun EchoFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    var isAnimating by remember { mutableStateOf(false) }

    val firstRingScale by animateFloatAsState(
        targetValue = if (isAnimating) 1.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "firstRing"
    )

    val secondRingScale by animateFloatAsState(
        targetValue = if (isAnimating) 1.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "secondRing"
    )
    var offsetX by remember { mutableStateOf(0f) }

    var globalPositionOfCancelButton by remember { mutableStateOf(IntOffset(0, 0)) }
    var globalPositionOfFAB by remember { mutableStateOf(IntOffset(0, 0)) }
    var initialPositionOfFAB by remember { mutableStateOf(IntOffset(0, 0)) }
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier.fillMaxWidth(0.4f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isAnimating) Arrangement.SpaceBetween else Arrangement.End
    ) {
        AnimatedVisibility(
            visible = isAnimating,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            BottomSheetIconButton(
                onClick = {},
                containerColor = MaterialTheme.colorScheme.errorContainer,
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Cancel Recording",
                        modifier = Modifier.size(14.dp)
                    )
                },
                size = if (abs(globalPositionOfCancelButton.x - globalPositionOfFAB.x) <= 50) 98.dp else 48.dp,
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    val position = layoutCoordinates.localToWindow(Offset.Zero)
                    globalPositionOfCancelButton = IntOffset(position.x.toInt(), position.y.toInt())
                }
            )
        }

        Box(
            modifier = modifier
                .offset {
                    IntOffset(
                        offsetX.roundToInt(),
                        0
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (isAnimating) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .scale(firstRingScale)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    AnimationGradientColor1.copy(alpha = 0.1f),
                                    AnimationGradientColor2.copy(alpha = 0.1f),
                                )
                            ),
                            shape = CircleShape
                        )

                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .scale(secondRingScale)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    AnimationGradientColor1.copy(alpha = 0.2f),
                                    AnimationGradientColor2.copy(alpha = 0.2f),
                                )
                            ),
                            shape = CircleShape
                        )
                )
            }



            Box(
                modifier = modifier
                    .size(56.dp) // Default FAB size
                    .shadow(
                        elevation = 10.dp, // Shadow elevation
                        shape = CircleShape,
                        ambientColor = BlueGradient1,
                        spotColor = BlueGradient1
                    )
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                BlueGradient1,
                                PrimaryContainer
                            )
                        )
                    )

                    .onGloballyPositioned { layoutCoordinates ->
                        // Get the global position
                        val position = layoutCoordinates.localToWindow(Offset.Zero)
                        globalPositionOfFAB = IntOffset(position.x.toInt(), position.y.toInt())
                    }


                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                initialPositionOfFAB = globalPositionOfFAB
                                isAnimating = true
                            },
                            onDragCancel =
                            {
                                isAnimating = false
                            },
                            onDragEnd = {

                                if (abs(globalPositionOfCancelButton.x - globalPositionOfFAB.x) <= 50) {
                                    // Cancel recording
                                    isAnimating = false
                                } else {
                                    isAnimating = false
                                }
                                offsetX = 0f
                            }, onDrag = { change, dragAmount ->


                                if (globalPositionOfCancelButton.x - globalPositionOfFAB.x < dragAmount.x && (globalPositionOfFAB.x - initialPositionOfFAB.x <= 0)) {
                                    change.consume()

                                    offsetX += dragAmount.x
                                }
                            }
                        )
                    }
                    .clickable(
                        enabled = !isAnimating
                    ) { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (isAnimating) R.drawable.ic_mic else R.drawable.ic_add
                    ),
                    contentDescription = stringResource(R.string.recording),
                )
            }
        }


    }

}

@Preview
@Composable
private fun EchoFloatingActionButtonPreview() {
    EchoJournalTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            EchoFloatingActionButton(
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        tint = Color.White,
                        contentDescription = null
                    )
                    // Icon
                })
        }


    }
}