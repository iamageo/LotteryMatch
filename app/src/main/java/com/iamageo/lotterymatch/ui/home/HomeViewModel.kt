package com.iamageo.lotterymatch.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamageo.lotterymatch.domain.model.InvalidLotteryException
import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.usecases.LotteryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lotteryUseCases: LotteryUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeStates())
    val state: State<HomeStates> = _state

    private var recentlyDeletedGame: LotteryGame? = null

    private var getGamesJob: Job? = null

    init {
        getGames()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.DeleteGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    lotteryUseCases.deleteGame(event.game)
                    recentlyDeletedGame = event.game
                }
            }
            is HomeEvents.RestoreGame -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        lotteryUseCases.addGame(recentlyDeletedGame ?: return@withContext)
                    }
                    recentlyDeletedGame = null
                }
            }
            is HomeEvents.ChangeTheme -> {
                _state.value = state.value.copy(isDarkTheme = !state.value.isDarkTheme)
            }
        }
    }

    private fun getGames() {
        getGamesJob?.cancel()
        getGamesJob = lotteryUseCases.getAllGames().onEach { games ->
            _state.value = state.value.copy(games = games)
        }.launchIn(viewModelScope)
    }

    fun filterAndSortGames(sortedNumbers: Set<Int>) {
        viewModelScope.launch {
            lotteryUseCases.getAllGames().collect { games ->
                val gamesWithMatchCount = games.map { game ->
                    Pair(game, countMatches(game, sortedNumbers))
                }.sortedByDescending { it.second }

                val sortedGames = gamesWithMatchCount.map { it.first }
                _state.value = state.value.copy(games = sortedGames)
            }
        }
    }

    companion object {
        fun countMatches(game: LotteryGame, sortedNumbers: Set<Int>): Int {
            val gameNumbers = game.game.split(",").mapNotNull { it.trim().toIntOrNull() }
            return gameNumbers.count { it in sortedNumbers }
        }
    }

}