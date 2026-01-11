package com.profs.languageapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: DomainService
) : ViewModel() {

}