package com.profs.languageapp.presentation.screens.game

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.domain.model.AnswerOption
import com.profs.languageapp.domain.model.RoundType
import com.profs.languageapp.data.models.response.ComplexQuestionResponse
import com.profs.languageapp.data.models.response.SimpleQuestionResponse
import com.profs.languageapp.data.repository.UserRepository
import com.profs.languageapp.data.utils.Constants
import com.profs.languageapp.domain.service.DomainService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcersiseViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: DomainService,
    private val repository: UserRepository,
) : ViewModel() {

    val currentUser = repository.currentUser

    private val _animalPage = MutableStateFlow(0)
    val animalPage: StateFlow<Int> = _animalPage

    private val _simpleAnswer = MutableStateFlow("")
    val simpleAnswer: StateFlow<String> = _simpleAnswer
    private val _complexAnswer = MutableStateFlow("")
    val complexAnswer: StateFlow<String> = _complexAnswer

    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language


    fun onSimpleAnswerChange(simpleAnswer: String) {
        _simpleAnswer.value = simpleAnswer
    }

    fun onComplexAnswerChange(complexAnswer: String) {
        _complexAnswer.value = complexAnswer
    }

    private var simplePool: MutableList<SimpleQuestionResponse> = mutableListOf()
    private var complexPool: MutableList<ComplexQuestionResponse> = mutableListOf()

    private val _currentSimple = MutableStateFlow<SimpleQuestionResponse?>(null)
    val currentSimple: Flow<SimpleQuestionResponse?> = _currentSimple
    private val _currentComplex = MutableStateFlow<ComplexQuestionResponse?>(null)
    val currentComplex: Flow<ComplexQuestionResponse?> = _currentComplex

    init {
        loadQuestions()
        getLanguage()
    }

    fun getLanguage(){
        viewModelScope.launch {
            _language.value = Constants.getLanguage(context).toString()
        }
    }

    fun savePage(page: Int) {
        _animalPage.value = page
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


    private fun loadQuestions() {
        viewModelScope.launch {
            simplePool = service.getAllSimpleQuestions()?.toMutableList() ?: mutableListOf()
            complexPool = service.getAllComplexQuestions()?.toMutableList() ?: mutableListOf()

            nextSimple()
            nextComplex()
            prepareComplexRound()
        }
    }


    private var lastQuestion: ComplexQuestionResponse? = null

    fun nextComplex() {
        val candidates = complexPool.filter { it != lastQuestion }
        val question = candidates.randomOrNull() ?: complexPool.random()
        lastQuestion = question
        _currentComplex.value = question
    }

    fun checkSimpleAnswer(simpleAnswer: String): Boolean {
        return if (_language.value == "en") {
            _currentSimple.value?.enAnswer == simpleAnswer
        } else {
            _currentSimple.value?.ruAnswer == simpleAnswer
        }
    }

    private val _roundType = MutableStateFlow(RoundType.EN_TO_RU)
    val roundType: StateFlow<RoundType> = _roundType

    private val _options = MutableStateFlow<List<AnswerOption>>(emptyList())
    val options: StateFlow<List<AnswerOption>> = _options

    private val _answered = MutableStateFlow(false)
    val answered: StateFlow<Boolean> = _answered

    suspend fun modifyUserRating(delta: Int) {
       service.modifyUserRating(userId = currentUser.value?.id, delta = delta)
    }

    fun prepareComplexRound() {
        val question = _currentComplex.value ?: return

        val correct = if (_roundType.value == RoundType.EN_TO_RU) {
            question.ruName
        } else {
            question.enName
        }

        val pool = complexPool
            .filter { it != question }.shuffled().take(3).map {
                if (_roundType.value == RoundType.EN_TO_RU)
                    it.ruName
                else
                    it.enName
            }

        val options = (pool + correct)
            .shuffled().map { text ->
                AnswerOption(
                    text = text,
                    isCorrect = text == correct
                )
            }

        _options.value = options
        _answered.value = false
    }

    private var successStreak = 0

    private fun calculateScore(isCorrect: Boolean): Int {
        return if (isCorrect) {
            successStreak++
            if (successStreak >= 2) {
                (1 + 0.2 * successStreak).toInt()
            } else {
                1
            }
        } else {
            successStreak = 0
            0
        }
    }

    fun selectAnswer(option: AnswerOption) {
        if (_answered.value) return

        _options.value = _options.value.map {
            it.copy(isSelected = it.text == option.text)
        }
    }

    fun nextRound() {
        _roundType.value =
            if (_roundType.value == RoundType.EN_TO_RU) RoundType.RU_TO_EN else RoundType.EN_TO_RU

        nextComplex()
        prepareComplexRound()

        _answered.value = false
    }

    fun checkAnswer() {
        if (_answered.value) return

        val selected = _options.value.find { it.isSelected } ?: return
        val isCorrect = selected.isCorrect

        val score = calculateScore(isCorrect)
        if (score > 0) {
            viewModelScope.launch {
                modifyUserRating(score)
            }
        }

        _answered.value = true
    }
}