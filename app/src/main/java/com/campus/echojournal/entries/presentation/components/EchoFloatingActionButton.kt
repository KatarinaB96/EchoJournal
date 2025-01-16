package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.BlueGradient1
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.PrimaryContainer

@Composable
fun EchoFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Box(
        modifier = modifier.run {
            size(56.dp) // Default FAB size
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
                .clickable(onClick = onClick)
        },
        contentAlignment = Alignment.Center
    ) {
        icon()
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