package com.profs.languageapp.presentation.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.User
import com.profs.languageapp.presentation.theme.DefaultBlack
import com.profs.languageapp.presentation.theme.GrayLight
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Typography


@Composable
fun LanguageCard(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .background(
                if (isSelected) Orange else Orange.copy(alpha = 0.1f),
                RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            stringResource(language.displayNameResId),
            style = Typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun ExcersiseCard(excersise: Excersise, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .size(width = 153.dp, height = 117.dp)
            .background(excersise.color, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(excersise.image),
            contentDescription = null,
            modifier = Modifier.size(90.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(excersise.name, style = Typography.bodySmall)
    }
}

@Composable
fun TopUserCard(topUser: User) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(GrayLight, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Spacer(modifier = Modifier.width(17.dp))

            Image(
                painterResource(topUser.image),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }

        Text(
            topUser.name,
            style = Typography.bodyLarge.copy(color = DefaultBlack, textAlign = TextAlign.Start),
            modifier = Modifier.width(150.dp)
        )

        Row {
            Text(
                "${topUser.points} points",
                style = Typography.bodyLarge.copy(color = DefaultBlack)
            )

            Spacer(modifier = Modifier.width(13.dp))
        }
    }
}