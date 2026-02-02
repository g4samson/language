package com.profs.languageapp.domain.usecase

import com.profs.languageapp.data.model.User
import com.profs.languageapp.domain.repository.UserRepository
import javax.inject.Inject

class SetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.setUser(user)
    }
}