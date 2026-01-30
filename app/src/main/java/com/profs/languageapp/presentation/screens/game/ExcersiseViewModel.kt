package com.profs.languageapp.presentation.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.User
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.ComplexQuestionResponse
import com.profs.languageapp.data.models.response.SimpleQuestionResponse
import com.profs.languageapp.data.repository.UserRepository
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcersiseViewModel @Inject constructor(
    private val service: DomainService,
    private val repository: UserRepository,
) : ViewModel() {

    private val _simpleAnswer = MutableStateFlow("")
    val simpleAnswer: StateFlow<String> = _simpleAnswer

    fun onSimpleAnswerChange(simpleAnswer: String) {
        _simpleAnswer.value = simpleAnswer
    }

    private var simplePool: MutableList<SimpleQuestionResponse> = mutableListOf()
    private var complexPool: MutableList<ComplexQuestionResponse> = mutableListOf()

    private val _currentSimple = MutableStateFlow<SimpleQuestionResponse?>(null)
    val currentSimple: Flow<SimpleQuestionResponse?> = _currentSimple
    private val _currentComplex = MutableStateFlow<ComplexQuestionResponse?>(null)
    val currentComplex: Flow<ComplexQuestionResponse?> = _currentComplex

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            simplePool = service.getAllSimpleQuestions()?.toMutableList() ?: mutableListOf()
            complexPool = service.getAllComplexQuestions()?.toMutableList() ?: mutableListOf()

            nextSimple()
            nextComplex()
        }
    }

    fun nextSimple() {
        if (simplePool.isEmpty()) {
            _currentSimple.value = null
            return
        }

        val question = simplePool.random()
        simplePool.remove(question)
        _currentSimple.value = question
    }

    fun nextComplex() {
        if (complexPool.isEmpty()) {
            _currentComplex.value = null
            return
        }

        val question = complexPool.random()
        complexPool.remove(question)
        _currentComplex.value = question
    }

    fun checkSimpleAnswer(simpleAnswer: String): Boolean {
        return _currentSimple.value?.enAnswer == simpleAnswer || _currentSimple.value?.ruAnswer == simpleAnswer
    }
}