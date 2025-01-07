package com.campus.echojournal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    primaryContainer = PrimaryContainer,
    secondary = Secondary,
    secondaryContainer = SecondaryContainer,
    onError = OnError,
    onErrorContainer = OnErrorContainer,
    errorContainer = ErrorContainer,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    inverseOnSurface = InverseOnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    onPrimary = OnPrimary,
    outline = Outline,
    outlineVariant = OutlineVariant,
    background = Background
)

@Composable
fun EchoJournalTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}