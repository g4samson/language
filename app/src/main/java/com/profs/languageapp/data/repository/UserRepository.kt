package com.profs.languageapp.data.repository

import com.profs.languageapp.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun setUser(user: User) { _currentUser.value = user }
}