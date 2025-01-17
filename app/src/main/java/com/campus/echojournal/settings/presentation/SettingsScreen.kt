package com.campus.echojournal.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.campus.echojournal.R
import com.campus.echojournal.settings.presentation.components.FilterChipDropdown
import com.campus.echojournal.settings.presentation.components.MoodCard
import com.campus.echojournal.ui.theme.OnSurface
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = stringResource(R.string.settings), color = OnSurface)
            },
            navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = stringResource(R.string.back)
                    )

                }
            }
        )
    }) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            MoodCard(  state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                })
            Spacer(Modifier.height(16.dp))
            FilterChipDropdown(
                state = state,
                onAction = { action ->
                    viewModel.onAction(action)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
