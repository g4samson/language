package com.profs.languageapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.model.response.CategoryResponse
import com.profs.languageapp.data.model.response.UserRatingResponse
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: DomainService,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val currentUser = getCurrentUserUseCase()

    private val _categoriesList = MutableStateFlow<List<CategoryResponse>?>(listOf())
    val categoriesList: Flow<List<CategoryResponse>?> = _categoriesList

    private val _userRatingList = MutableStateFlow<List<UserRatingResponse>?>(listOf())
    val userRatingList: Flow<List<UserRatingResponse>?> = _userRatingList

    init {
        getCategories()
        getUserRating()
    }

    fun getCategories() {
        viewModelScope.launch {
            val categories = service.getAvailableCategories()
            _categoriesList.emit(categories?.map { it })
        }
    }

    fun getUserRating() {
        viewModelScope.launch {
            val userRating = service.getUserRating()
            _userRatingList.emit(
                userRating
                    ?.sortedByDescending { it.rating }
                    ?.take(3)
            )
        }
    }
}