package com.magnus.playfut.ui.features.initializer

import androidx.lifecycle.ViewModel
import com.magnus.playfut.ui.domain.repository.UserRepository

class InitializerViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

}