package com.profs.languageapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.profs.languageapp.data.models.Language
import com.profs.languageapp.data.models.User
import com.profs.languageapp.data.models.response.CategoryResponse
import com.profs.languageapp.data.models.response.UserRatingResponse
import com.profs.languageapp.data.repository.UserRepository
import com.profs.languageapp.domain.service.DomainService
import com.profs.languageapp.presentation.screens.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: DomainService,
    private val repository: UserRepository
) : ViewModel() {
    private val _categoriesList = MutableStateFlow<List<CategoryResponse>?>(listOf())
    val categoriesList: Flow<List<CategoryResponse>?> = _categoriesList

    private val _userRatingList = MutableStateFlow<List<UserRatingResponse>?>(listOf())
    val userRatingList: Flow<List<UserRatingResponse>?> = _userRatingList

    val currentUser = repository.currentUser

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