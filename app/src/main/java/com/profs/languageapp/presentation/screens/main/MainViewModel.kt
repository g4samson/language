package com.profs.languageapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.Excersise
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.User
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: DomainService
) : ViewModel() {
    private val _excersiseList = MutableStateFlow<List<Excersise>?>(listOf())
    val excersiseList: Flow<List<Excersise>?> = _excersiseList

    private val _topUserList = MutableStateFlow<List<User>?>(listOf())
    val topUserList: Flow<List<User>?> = _topUserList

    init {
        getExcersises()
        getTopUsers()
    }

    fun getExcersises() {
        viewModelScope.launch {
            val excersises = service.getExercises()
            _excersiseList.emit(excersises.map { it })
        }
    }

    fun getTopUsers() {
        viewModelScope.launch {
            val topUsers = service.getTopUsers()
            _topUserList.emit(topUsers.map { it })
        }
    }
}