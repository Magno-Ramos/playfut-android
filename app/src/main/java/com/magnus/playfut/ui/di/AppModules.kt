package com.magnus.playfut.ui.di

import com.magnus.playfut.ui.domain.repository.GroupsRepository
import com.magnus.playfut.ui.domain.repository.UserRepository
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.initializer.InitializerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { UserRepository() }
    single { GroupsRepository() }

    viewModel { InitializerViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}