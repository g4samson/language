package com.profs.languageapp.domain.usecase

import android.graphics.Bitmap
import android.graphics.Rect
import javax.inject.Inject

class CropImageUseCase @Inject constructor() {

    operator fun invoke(
        bitmap: Bitmap,
        cropRect: Rect
    ): Bitmap {
        return Bitmap.createBitmap(
            bitmap,
            cropRect.left,
            cropRect.top,
            cropRect.width(),
            cropRect.height()
        )
    }
}