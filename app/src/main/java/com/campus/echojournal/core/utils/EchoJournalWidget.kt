package com.campus.echojournal.core.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.size
import com.campus.echojournal.MainActivity
import com.campus.echojournal.R

class EchoJournalWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        Column(
            modifier = GlanceModifier
                .cornerRadius(20.dp)
                .clickable(onClick = actionStartActivity<MainActivity>()),
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = GlanceModifier.size(182.dp),
                provider = ImageProvider(R.drawable.widget),
                contentDescription = null,
            )
        }
    }

    @Preview
    @Composable
    fun WidgetContentPreview() {
        Content()
    }
}