package com.profs.languageapp.domain.usecase

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import jakarta.inject.Inject
import java.io.File

class OpenPdfUseCase @Inject constructor() {

    fun openPdf(context: Context, fileName: String) {
        val pdfFile = File(context.filesDir, fileName)

        if (!pdfFile.exists()) {
            context.assets.open(fileName).use { input ->
                pdfFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        val uri = FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            pdfFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        context.startActivity(intent)
    }
}