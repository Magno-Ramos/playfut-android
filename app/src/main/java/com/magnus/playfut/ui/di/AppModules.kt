package com.magnus.playfut.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.database.AppDatabase
import com.magnus.playfut.ui.domain.repository.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.RemoteGroupRepository
import com.magnus.playfut.ui.domain.repository.RemotePlayerRepository
import com.magnus.playfut.ui.features.groups.form.GroupsFormViewModel
import com.magnus.playfut.ui.features.groups.menu.GroupMenuViewModel
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsViewModel
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.player.form.PlayerFormViewModel
import com.magnus.playfut.ui.features.player.list.PlayerListViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().groupDao() }
    single { get<AppDatabase>().playerDao() }
    single { FirebaseAuth.getInstance() }

    single { RemoteGroupRepository(get()) }
    single { LocalGroupRepository(get()) }

    single { RemotePlayerRepository() }
    single { LocalPlayerRepository(get()) }

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { GroupsFormViewModel(get(), get(), get()) }
    viewModel { GroupMenuViewModel(get(), get(), get()) }
    viewModel { GroupSettingsViewModel(get(), get(), get()) }
    viewModel { PlayerListViewModel(get(), get(), get()) }
    viewModel { PlayerFormViewModel(get(), get(), get()) }
    viewModel { RoundSortViewModel(get(), get(), get()) }
}