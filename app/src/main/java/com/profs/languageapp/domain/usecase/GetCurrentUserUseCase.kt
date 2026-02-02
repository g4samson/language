package com.profs.languageapp.domain.usecase

import com.profs.languageapp.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.currentUser
}