package com.profs.languageapp.presentation.composable

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.profs.languageapp.presentation.theme.DeepBlue
import com.profs.languageapp.presentation.theme.DefaultWhite

class WidgetLeaderboard : GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        provideContent {
//            val users: List<UserRatingResponse>? =
//                DomainServiceImpl().getUserRating()

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(DeepBlue)
                    .padding(18.dp)
            ) {
                Text(
                    "You place is 7! Awesome!",
                    style = TextStyle(
                        color = ColorProvider(DefaultWhite),
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(GlanceModifier.height(11.dp))

//                users.forEachIndexed { index, user ->
//                    LeaderboardWidgetCard(index + 1, user)
//                    Spacer(modifier = GlanceModifier.height(6.dp))
//                }
            }
        }
    }
}

class WidgetLeaderboardReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = WidgetLeaderboard()
}

//@SuppressLint("RestrictedApi")
//@Composable
//fun LeaderboardWidgetCard(index: Int, user: User) {
//    Row(
//        modifier = GlanceModifier
//            .fillMaxWidth()
//            .height(38.dp)
//            .background(GrayLight)
//            .padding(horizontal = 12.dp).cornerRadius(20.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            "$index.",
//            style = TextStyle(color = ColorProvider(DefaultBlack), fontWeight = FontWeight.Medium)
//        )
//        Spacer(GlanceModifier.width(8.dp))
//
//        Image(
//            provider = ImageProvider(user.image),
//            contentDescription = null,
//            modifier = GlanceModifier.size(24.dp)
//        )
//
//        Spacer(GlanceModifier.width(21.dp))
//
//        Text(
//            user.name,
//            style = TextStyle(color = ColorProvider(DefaultBlack), fontWeight = FontWeight.Medium)
//        )
//
//        Spacer(GlanceModifier.width(10.dp))
//
//        Text(
//            "${user.points} points",
//            maxLines = 1,
//            style = TextStyle(color = ColorProvider(DefaultBlack), fontWeight = FontWeight.Medium)
//        )
//
//    }
//}