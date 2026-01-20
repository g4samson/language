package com.profs.languageapp.domain.usecase

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import java.io.File

class TakePhotoUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun createImageUri(): Uri {
        val file = File.createTempFile(
            "camera_image",
            ".jpg",
            context.cacheDir
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}