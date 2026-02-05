package com.profs.languageapp.presentation.screens.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.SetUserUseCase
import com.profs.languageapp.domain.usecase.ValidateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WidgetViewModel @Inject constructor(
    private val service: DomainService,
) : ViewModel() {

    private val _logged = MutableStateFlow(false)
    val logged : StateFlow<Boolean> = _logged

    fun logged(i: Boolean){
        _logged.value = i
    }
}
