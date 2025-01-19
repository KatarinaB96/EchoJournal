package com.campus.echojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.campus.echojournal.core.utils.Route
import com.campus.echojournal.entries.presentation.EntriesListScreenRoot
import com.campus.echojournal.settings.presentation.SettingsScreen
import com.campus.echojournal.settings.presentation.SettingsViewModel
import com.campus.echojournal.ui.theme.EchoJournalTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.EchoGraph
                ) {
                    navigation<Route.EchoGraph>(
                        startDestination = Route.HomeScreen
                    ) {
                        composable<Route.HomeScreen>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            EntriesListScreenRoot(
                                onSettingsClick = {
                                    navController.navigate(Route.SettingsScreen)
                                }
                            )
                        }
                        composable<Route.SettingsScreen>(
                            exitTransition = { slideOutHorizontally() },
                            popEnterTransition = {
                                slideInHorizontally()
                            }
                        ) {
                            val viewModel = koinViewModel<SettingsViewModel>()
                            SettingsScreen(viewModel)
                        }
                    }


                }


            }
        }
    }
}

