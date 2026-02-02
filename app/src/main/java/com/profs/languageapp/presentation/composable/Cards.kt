package com.profs.languageapp.presentation.composable

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.profs.languageapp.data.model.Language
import com.profs.languageapp.data.model.response.CategoryResponse
import com.profs.languageapp.data.model.response.UserRatingResponse
import com.profs.languageapp.presentation.theme.Blue
import com.profs.languageapp.presentation.theme.DefaultBlack
import com.profs.languageapp.presentation.theme.GrayLight
import com.profs.languageapp.presentation.theme.Green
import com.profs.languageapp.presentation.theme.Orange
import com.profs.languageapp.presentation.theme.Red
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
fun CategoryCard(category: CategoryResponse, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .size(width = 153.dp, height = 117.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(when (category.name){
                "Guess the animal" -> Blue
                "Word practice" -> Red
                "Audition" -> Orange
                "Game" -> Green
                else -> DefaultBlack
            })
            .padding(horizontal = 12.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Base64Image(
            base64 = category.image,
            modifier = Modifier.size(90.dp),
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(category.name, style = Typography.bodySmall)
    }
}

@Composable
fun Base64Image(base64: String, modifier: Modifier = Modifier) {
    val bitmap = runCatching {
        val pureBase64 = base64.substringAfter(",")
        val bytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?.copy(Bitmap.Config.ARGB_8888, true)
    }.getOrNull()

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    }
}

@Composable
fun MainUserCard(user: UserRatingResponse) {

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

            AsyncImage(
                model = user.image,
                contentDescription = null,
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(18.dp))
            )
        }

        Text(
            user.firstName,
            style = Typography.bodyLarge.copy(color = DefaultBlack, textAlign = TextAlign.Start),
            modifier = Modifier.width(150.dp)
        )

        Row {
            Text(
                "${user.rating} points",
                style = Typography.bodyLarge.copy(color = DefaultBlack)
            )

            Spacer(modifier = Modifier.width(13.dp))
        }
    }
}