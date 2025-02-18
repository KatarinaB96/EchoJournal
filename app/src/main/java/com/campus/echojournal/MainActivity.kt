package com.campus.echojournal

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.campus.echojournal.core.utils.Route
import com.campus.echojournal.entries.presentation.entries_list.EntriesListScreenRoot
import com.campus.echojournal.entries.presentation.entries_list.EntriesViewModel
import com.campus.echojournal.entries.presentation.new_entry.NewEntryScreenRoot
import com.campus.echojournal.entries.presentation.new_entry.NewEntryViewModel
import com.campus.echojournal.settings.presentation.SettingsScreenRoot
import com.campus.echojournal.settings.presentation.SettingsViewModel
import com.campus.echojournal.ui.theme.EchoJournalTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.RECORD_AUDIO
            ), 0
        )

        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()
                var startedRecording by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val intentStartRecording = intent?.getBooleanExtra("START_RECORDING", false) ?: false
                    if (intentStartRecording) {
                        startedRecording = true
                    }
                }
                Log.d("Widget", "START_RECORDING received: $startedRecording")
                NavHost(
                    navController = navController,
                    startDestination = Route.EchoGraph
                ) {
                    navigation<Route.EchoGraph>(startDestination = Route.HomeScreen) {
                        composable<Route.HomeScreen>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = { slideInHorizontally() }
                        ) {
                            val viewModel = koinViewModel<EntriesViewModel>()
                            if (startedRecording) {
                                viewModel.setStartRecording(startedRecording)
                            }
                            startedRecording = false

                            EntriesListScreenRoot(
                                viewModel = viewModel,
                                onSettingsClick = {
                                    navController.navigate(Route.SettingsScreen)
                                },
                                onNavigateAddEntryScreen = { fileUri ->
                                    navController.navigate(Route.AddEntryScreen(fileUri))
                                }
                            )
                        }


                        composable<Route.AddEntryScreen>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            val args = it.toRoute<Route.AddEntryScreen>()
                            val viewModel = koinViewModel<NewEntryViewModel>()
                            NewEntryScreenRoot(
                                viewModel = viewModel,
                                onBackClick = {
                                    if (navController.previousBackStackEntry != null) {
                                        navController.navigateUp()
                                    } else {
                                        navController.navigate(Route.HomeScreen)
                                    }
                                },
                                path = args.fileUri
                            )
                        }
                        composable<Route.SettingsScreen>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            val viewModel = koinViewModel<SettingsViewModel>()
                            SettingsScreenRoot(viewModel = viewModel,
                                onBackClick = {
                                    if (navController.previousBackStackEntry != null) {
                                        navController.navigateUp()
                                    } else {
                                        navController.navigate(Route.HomeScreen)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

