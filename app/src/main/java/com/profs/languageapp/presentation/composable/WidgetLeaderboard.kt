package com.profs.languageapp.presentation.composable

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.padding
import androidx.glance.text.Text

class WidgetLeaderboard : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            Text(
                text = "Widget",
                modifier = GlanceModifier.padding(16.dp)
            )
        }
    }
}

class WidgetLeaderboardReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = WidgetLeaderboard()
}