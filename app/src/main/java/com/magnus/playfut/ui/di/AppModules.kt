package com.magnus.playfut.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.database.AppDatabase
import com.magnus.playfut.ui.domain.repository.GroupRepository
import com.magnus.playfut.ui.domain.repository.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.RemotePlayerRepository
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.repository.local.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteGroupRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteRoundRepository
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
    single { GroupRepository(get(), get(), get()) }

    single { LocalRoundRepository(get()) }
    single { RemoteRoundRepository() }
    single { RoundRepository(get(), get(), get()) }

    single { RemotePlayerRepository() }
    single { LocalPlayerRepository(get()) }

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { GroupsFormViewModel(get()) }
    viewModel { GroupMenuViewModel(get()) }
    viewModel { GroupSettingsViewModel(get()) }
    viewModel { PlayerListViewModel(get()) }
    viewModel { PlayerFormViewModel(get(), get(), get()) }
    viewModel { RoundSortViewModel(get(), get(), get()) }
}