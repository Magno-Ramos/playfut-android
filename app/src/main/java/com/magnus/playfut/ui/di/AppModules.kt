package com.magnus.playfut.ui.di

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.ui.domain.database.AppDatabase
import com.magnus.playfut.ui.domain.repository.GroupRepository
import com.magnus.playfut.ui.domain.repository.MatchRepository
import com.magnus.playfut.ui.domain.repository.PlayerRepository
import com.magnus.playfut.ui.domain.repository.RoundRepository
import com.magnus.playfut.ui.domain.repository.ScoreRepository
import com.magnus.playfut.ui.domain.repository.TeamRepository
import com.magnus.playfut.ui.domain.repository.local.LocalGroupRepository
import com.magnus.playfut.ui.domain.repository.local.LocalMatchRepository
import com.magnus.playfut.ui.domain.repository.local.LocalPlayerRepository
import com.magnus.playfut.ui.domain.repository.local.LocalRoundRepository
import com.magnus.playfut.ui.domain.repository.local.LocalScoreRepository
import com.magnus.playfut.ui.domain.repository.local.LocalTeamRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteGroupRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteMatchRepository
import com.magnus.playfut.ui.domain.repository.remote.RemotePlayerRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteRoundRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteScoreRepository
import com.magnus.playfut.ui.domain.repository.remote.RemoteTeamRepository
import com.magnus.playfut.ui.features.groups.form.GroupsFormViewModel
import com.magnus.playfut.ui.features.groups.menu.GroupMenuViewModel
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsViewModel
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.player.form.PlayerFormViewModel
import com.magnus.playfut.ui.features.player.list.PlayerListViewModel
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().groupDao() }
    single { get<AppDatabase>().playerDao() }
    single { get<AppDatabase>().roundDao() }
    single { get<AppDatabase>().matchDao() }
    single { get<AppDatabase>().scoreDao() }
    single { get<AppDatabase>().teamDao() }
    single { FirebaseAuth.getInstance() }

    single { RemoteGroupRepository(get()) }
    single { LocalGroupRepository(get()) }
    single { GroupRepository(get(), get(), get()) }

    single { LocalRoundRepository(get()) }
    single { RemoteRoundRepository() }
    single { RoundRepository(get(), get(), get()) }

    single { RemotePlayerRepository() }
    single { LocalPlayerRepository(get()) }
    single { PlayerRepository(get(), get(), get()) }

    single { LocalScoreRepository(get()) }
    single { RemoteScoreRepository() }
    single { ScoreRepository(get(), get(), get()) }

    single { LocalTeamRepository(get()) }
    single { RemoteTeamRepository() }
    single { TeamRepository(get(), get(), get()) }

    single { LocalMatchRepository(get()) }
    single { RemoteMatchRepository() }
    single { MatchRepository(get(), get(), get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { GroupsFormViewModel(get()) }
    viewModel { GroupMenuViewModel(get()) }
    viewModel { GroupSettingsViewModel(get()) }
    viewModel { PlayerListViewModel(get()) }
    viewModel { PlayerFormViewModel(get()) }
    viewModel { RoundSortViewModel(get(), get()) }
    viewModel { RoundPlayingViewModel(get(), get(), get(), get(), get()) }
}