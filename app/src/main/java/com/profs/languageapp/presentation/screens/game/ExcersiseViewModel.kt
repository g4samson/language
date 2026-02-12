package com.profs.languageapp.presentation.screens.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.profs.languageapp.data.model.AnswerOption
import com.profs.languageapp.data.model.RoundType
import com.profs.languageapp.data.model.response.AnimalImageResponse
import com.profs.languageapp.data.model.response.ComplexQuestionResponse
import com.profs.languageapp.data.model.response.SimpleQuestionResponse
import com.profs.languageapp.data.utils.AnimalClassifier
import com.profs.languageapp.data.utils.LanguagePreferences
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.common.FileUtil.loadLabels
import javax.inject.Inject

@HiltViewModel
class ExcersiseViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: DomainService,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val classifier by lazy { AnimalClassifier(context) }
    private val labels by lazy { classifier.loadLabels(context) }

    val currentUser = getCurrentUserUseCase()

    private val _animalPage = MutableStateFlow(0)
    val animalPage: StateFlow<Int> = _animalPage

    private val _simpleAnswer = MutableStateFlow("")
    val simpleAnswer: StateFlow<String> = _simpleAnswer


    private val _language = MutableStateFlow("")
    val language: StateFlow<String> = _language


    fun onSimpleAnswerChange(simpleAnswer: String) {
        _simpleAnswer.value = simpleAnswer
    }
    private val _currentSimple = MutableStateFlow<AnimalImageResponse?>(null)
    val currentSimple: Flow<AnimalImageResponse?> = _currentSimple


//    private var simplePool: MutableList<SimpleQuestionResponse> = mutableListOf()
//    private var complexPool: MutableList<ComplexQuestionResponse> = mutableListOf()

    private var simplePool: MutableList<AnimalImageResponse> = mutableListOf()
    private var complexPool: MutableList<ComplexQuestionResponse> = mutableListOf()


    private val _currentComplex = MutableStateFlow<ComplexQuestionResponse?>(null)
    val currentComplex: Flow<ComplexQuestionResponse?> = _currentComplex

    private val _complexAnswer = MutableStateFlow("")
    val complexAnswer: StateFlow<String> = _complexAnswer

    fun onComplexAnswerChange(complexAnswer: String) {
        _complexAnswer.value = complexAnswer
    }

    init {
        loadQuestions()
        //getLanguage()
    }

    fun getLanguage(){
        viewModelScope.launch {
            _language.value = LanguagePreferences.getLanguage(context).toString()
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
        //simplePool.remove(question)
        _currentSimple.value = question
    }


    private fun loadQuestions() {
        viewModelScope.launch {
            simplePool = service.getAnimalImages()?.toMutableList() ?: mutableListOf()
            //complexPool = service.getAllComplexQuestions()?.toMutableList() ?: mutableListOf()

            nextSimple()
           // nextComplex()
            //prepareComplexRound()
        }
    }

//    fun checkSimpleAnswer(simpleAnswer: String): Boolean {
//        return if (_language.value == "en") {
//            _currentSimple.value?.enAnswer == simpleAnswer
//        } else {
//            _currentSimple.value?.ruAnswer == simpleAnswer
//        }
//    }

    suspend fun checkWithTensorFlow(): Boolean {
        val imageUrl = _currentSimple.value?.image ?: return false

        val bitmap = loadBitmapFromUrl(context, imageUrl)
        val probs = classifier.classify(bitmap)

        if (probs.isEmpty() || labels.isEmpty()) return false

        val maxIdx = probs.indices.maxByOrNull { probs[it] } ?: return false

        if (maxIdx >= labels.size) return false

        val predicted = labels[maxIdx]
        val confidence = probs[maxIdx]

        Log.e("TF", "Predicted: $predicted  conf=$confidence")
        Log.e("TF_RAW", probs.joinToString())

        probs.withIndex()
            .sortedByDescending { it.value }
            .forEach {
                Log.e("TF_TOP", "idx=${it.index} val=${it.value}")
            }

        val user = _simpleAnswer.value.trim().lowercase()
        val model = predicted.lowercase()

        return confidence > 0.4f &&
                user.contains(model)
    }

    suspend fun loadBitmapFromUrl(
        context: Context,
        url: String
    ): Bitmap {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false)
            .build()

        val result = loader.execute(request) as SuccessResult
        return (result.drawable as BitmapDrawable).bitmap
    }

    private var lastQuestion: ComplexQuestionResponse? = null

    fun nextComplex() {
        val candidates = complexPool.filter { it != lastQuestion }
        val question = candidates.randomOrNull() ?: complexPool.random()
        lastQuestion = question
        _currentComplex.value = question
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