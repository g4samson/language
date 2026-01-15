package com.profs.languageapp.presentation.composable

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import com.profs.languageapp.data.models.User
import com.profs.languageapp.data.source.LocalDataSource
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.presentation.theme.DeepBlue

class WidgetLeaderboard : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
            val users: List<User> =
                LocalDataSource().getTopUsers()

            Column(modifier = GlanceModifier.fillMaxSize().background(DeepBlue)) {
                users.forEachIndexed { index, user ->
                    LeaderboardWidgetCard(index, user)
                }
            }
        }
    }
}

class WidgetLeaderboardReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = WidgetLeaderboard()
}