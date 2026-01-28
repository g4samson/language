package com.profs.languageapp.presentation.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.profs.languageapp.data.repository.UserRepository
import com.profs.languageapp.domain.usecase.PickImageFromGalleryUseCase
import com.profs.languageapp.domain.usecase.TakePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val pickImageFromGalleryUseCase: PickImageFromGalleryUseCase,
    private val takePhotoUseCase: TakePhotoUseCase,
    private val repository: UserRepository
) : ViewModel() {

    private val _changeState = MutableStateFlow(false)
    val changeState: StateFlow<Boolean> = _changeState

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _cameraImageUri = MutableStateFlow<Uri?>(null)
    val cameraImageUri: StateFlow<Uri?> = _cameraImageUri

    val currentUser = repository.currentUser

    fun openChangeImageDialog() {
        _changeState.value = true
    }

    fun closeChangeImageDialog() {
        _changeState.value = false
    }

    fun getGalleryIntent(): Intent {
        return pickImageFromGalleryUseCase.createIntent()
    }

    fun onImagePicked(uri: Uri) {
        _selectedImageUri.value = uri
    }

    fun createCameraUri(): Uri {
        val uri = takePhotoUseCase.createImageUri()
        _cameraImageUri.value = uri
        _selectedImageUri.value = uri
        return uri
    }
}