
package com.campus.echojournal.entries.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.campus.echojournal.R
import com.campus.echojournal.ui.theme.EchoJournalTheme
import com.campus.echojournal.ui.theme.GradientColor1
import com.campus.echojournal.ui.theme.GradientColor2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesListTopAppBar(
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    GradientColor1.copy(alpha = 0.8F),
                    GradientColor2.copy(alpha = 0.8F)
                ),
                start = Offset(0f, 0f),
                end = Offset(0f, Float.POSITIVE_INFINITY)
            )
        ),
        // I can't give the transparent color to the top app bar. So, I removed from Scaffold parameter.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        actions = {
            IconButton(onClick = { onSettingsClick() }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.settings),
                )
            }
        },
        title = {
            Text(stringResource(R.string.your_echojournal))
        },

        )
}

@Preview
@Composable
private fun EntriesListTopAppBarPreview() {
    EchoJournalTheme {
        EntriesListTopAppBar()
    }


}