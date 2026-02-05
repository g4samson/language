package com.profs.languageapp.presentation.composable

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.profs.languageapp.data.model.response.UserRatingResponse
import com.profs.languageapp.data.utils.Storage
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.service.DomainServiceImpl
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import com.profs.languageapp.presentation.screens.login.LoginViewModel
import com.profs.languageapp.presentation.screens.login.WidgetViewModel
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultBlack
import com.profs.languageapp.presentation.theme.DefaultWhite
import com.profs.languageapp.presentation.theme.GrayLight

class WidgetLeaderboardReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = WidgetLeaderboard()
}


class WidgetLeaderboard : GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        val viewModel = WidgetViewModel(DomainServiceImpl(storage = Storage()))
        val service = DomainServiceImpl(storage = Storage())

        val users = try {
            service.getUserRating()
        } catch (e: Exception) {
            null
        }

        val logged = viewModel.logged.value

        provideContent {
            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(DeepBlue)
                    .padding(18.dp)
            ) {
                if (logged) {

//                    // Предположим, что viewModel знает ID текущего пользователя
//                    val currentUserId = viewModel.getCurrentUserId() // Long?

                    // Найдём позицию в рейтинге
                    val position = users?.indexOfFirst { it.id.toInt() == 1 }?.plus(1) // +1, т.к. индекс с 0

                    position?.let {
                        Text(
                            text = if (it > 0)
                                "You place is $position! Awesome!"
                            else
                                "You are not in the leaderboard",
                            style = TextStyle(
                                color = ColorProvider(DefaultWhite),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Spacer(GlanceModifier.height(11.dp))

                    users?.forEachIndexed { index, user ->
                        LeaderboardWidgetCard(index + 1, user)
                        Spacer(modifier = GlanceModifier.height(6.dp))
                    }
                } else {
                    Text(
                        "LOGIN, YO!",
                        style = TextStyle(
                            color = ColorProvider(DefaultWhite),
                            fontWeight = FontWeight.Medium, fontSize = 26.sp
                        )
                    )
                }
            }
        }
    }

    @Composable
    @SuppressLint("RestrictedApi")
    fun LeaderboardWidgetCard(index: Int, user: UserRatingResponse) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .height(38.dp)
                .background(GrayLight)
                .padding(horizontal = 12.dp).cornerRadius(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "$index.",
                style = TextStyle(
                    color = ColorProvider(DefaultBlack),
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(GlanceModifier.width(8.dp))

            Spacer(GlanceModifier.width(21.dp))

            Text(
                user.firstName,
                style = TextStyle(
                    color = ColorProvider(DefaultBlack),
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(GlanceModifier.width(10.dp))

            Text(
                "${user.rating} points",
                maxLines = 1,
                style = TextStyle(
                    color = ColorProvider(DefaultBlack),
                    fontWeight = FontWeight.Medium
                )
            )

        }
    }
}


