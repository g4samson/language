package com.profs.languageapp.domain.usecase

import android.content.Intent
import jakarta.inject.Inject

class PickImageFromGalleryUseCase @Inject constructor() {

    fun createIntent(): Intent {
        return Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
    }
}