package com.magnus.playfut.di

import com.google.firebase.auth.FirebaseAuth
import com.magnus.playfut.domain.database.AppDatabase
import com.magnus.playfut.domain.repository.GroupRepository
import com.magnus.playfut.domain.repository.MatchRepository
import com.magnus.playfut.domain.repository.PlayerRepository
import com.magnus.playfut.domain.repository.PreferencesRepository
import com.magnus.playfut.domain.repository.RoundRepository
import com.magnus.playfut.domain.repository.ScoreRepository
import com.magnus.playfut.domain.repository.StatisticsRepository
import com.magnus.playfut.domain.repository.TeamRepository
import com.magnus.playfut.domain.repository.UserRepository
import com.magnus.playfut.ui.features.groups.form.GroupsFormViewModel
import com.magnus.playfut.ui.features.groups.menu.GroupMenuViewModel
import com.magnus.playfut.ui.features.groups.settings.GroupSettingsViewModel
import com.magnus.playfut.ui.features.home.HomeViewModel
import com.magnus.playfut.ui.features.player.form.PlayerFormViewModel
import com.magnus.playfut.ui.features.player.list.PlayerListViewModel
import com.magnus.playfut.ui.features.rounds.history.RoundHistoryViewModel
import com.magnus.playfut.ui.features.rounds.playing.RoundPlayingViewModel
import com.magnus.playfut.ui.features.rounds.playing.screens.RoundPlayingTeamViewModel
import com.magnus.playfut.ui.features.rounds.sorting.form.RoundSortViewModel
import com.magnus.playfut.ui.features.splash.SplashViewModel
import com.magnus.playfut.ui.features.statistic.home.StatisticHomeViewModel
import com.magnus.playfut.ui.features.statistic.ranking.RankingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().groupDao() }
    single { get<AppDatabase>().playerDao() }
    single { get<AppDatabase>().roundDao() }
    single { get<AppDatabase>().matchDao() }
    single { get<AppDatabase>().scoreDao() }
    single { get<AppDatabase>().teamDao() }
    single { get<AppDatabase>().schemaDao() }
    single { FirebaseAuth.getInstance() }

    single { PreferencesRepository(androidContext()) }

    single { UserRepository(get(), get()) }
    single { GroupRepository(get()) }
    single { PlayerRepository(get()) }
    single { MatchRepository(get()) }
    single { RoundRepository(get()) }
    single { ScoreRepository(get()) }
    single { TeamRepository(get(), get()) }
    single { StatisticsRepository(get(), get()) }

    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { GroupsFormViewModel(get()) }
    viewModel { GroupMenuViewModel(get()) }
    viewModel { GroupSettingsViewModel(get()) }
    viewModel { PlayerListViewModel(get()) }
    viewModel { PlayerFormViewModel(get()) }
    viewModel { RoundHistoryViewModel(get()) }
    viewModel { RoundPlayingTeamViewModel(get()) }
    viewModel { StatisticHomeViewModel(get(), get()) }
    viewModel { RankingViewModel(get()) }
    viewModel { RoundSortViewModel(get(), get(), get()) }
    viewModel { RoundPlayingViewModel(get(), get(), get(), get()) }
}