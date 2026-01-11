package com.profs.languageapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val service: DomainService
) : ViewModel() {

}