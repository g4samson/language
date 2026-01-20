package com.profs.languageapp.presentation.screens.profileResizePhoto

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.profs.languageapp.presentation.composable.CropImageComponent
import com.profs.languageapp.presentation.screens.profile.ProfileViewModel

@Composable
fun ProfileResizePhotoScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel
) {
    val imageUri by viewModel.selectedImageUri.collectAsState()
    val context = LocalContext.current

    if (imageUri == null) return

    // Decode bitmap once
    val imageBitmap by remember(imageUri) {
        mutableStateOf(
            decodeImageBitmap(context, imageUri!!)
        )
    }

    CropImageComponent(
        bitmap = imageBitmap,
        onSave = { croppedBitmap ->
            // TODO: save avatar
            navController.popBackStack()
        },
        onCancel = {
            navController.popBackStack()
        }
    )
}

fun decodeImageBitmap(
    context: Context,
    uri: Uri
): ImageBitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.setTargetSize(1024, 1024)
        }.asImageBitmap()
    } else {
        context.contentResolver.openInputStream(uri).use {
            BitmapFactory.decodeStream(it)
        }.asImageBitmap()
    }
}