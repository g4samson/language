package com.profs.languageapp.domain.repository

import com.profs.languageapp.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: StateFlow<User?> = _currentUser

    override suspend fun setUser(user: User) {
        _currentUser.value = user
    }

    override suspend fun clearUser() {
        _currentUser.value = null
    }
}