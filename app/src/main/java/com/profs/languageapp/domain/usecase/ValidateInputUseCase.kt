package com.profs.languageapp.domain.usecase

import javax.inject.Inject

class ValidateInputUseCase @Inject constructor() {

    private val emailPattern =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    fun isEmailValid(email: String): Boolean {
        return emailPattern.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpace = password.any { it == ' ' }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() && it != ' ' }

        return hasUpperCase &&
                hasLowerCase &&
                hasDigit &&
                hasSpace &&
                hasSpecialChar
    }

    fun confirmedPassword(password: String, confirmation: String) : Boolean {
        if (password == confirmation) return true else return false
    }
}









//
//package com.profs.languageapp.domain.usecase
//
//import android.content.Context
//import android.content.Intent
//import androidx.core.content.FileProvider
//import jakarta.inject.Inject
//import java.io.File
//
//class OpenPdfUseCase @Inject constructor() {
//
//    fun openPdf(context: Context, fileName: String) {
//        val pdfFile = File(context.filesDir, fileName)
//
//        if (!pdfFile.exists()) {
//            context.assets.open(fileName).use { input ->
//                pdfFile.outputStream().use { output ->
//                    input.copyTo(output)
//                }
//            }
//        }
//
//        val uri = FileProvider.getUriForFile(
//            context,
//            context.packageName + ".fileprovider",
//            pdfFile
//        )
//
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(uri, "application/pdf")
//            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//        }
//
//        context.startActivity(intent)
//    }
//} можешь полпробовать найтис этим кодом примерно доки на android.developers или чтото что поможет мне понять как пдф открыть