package com.profs.languageapp.domain.repository

import com.profs.languageapp.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val currentUser: StateFlow<User?>

    suspend fun setUser(user: User)
    suspend fun clearUser()
}