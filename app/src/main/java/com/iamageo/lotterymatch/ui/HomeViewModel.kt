package com.iamageo.lotterymatch.ui

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

    private var currentGameId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

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
            is HomeEvents.AddGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        lotteryUseCases.addGame(
                            LotteryGame(
                                game = "1,4,22,45,58,60",
                                timestamp = System.currentTimeMillis(),
                                id = currentGameId
                            )
                        )
                        _eventFlow.emit(UiEvents.AddGame)
                    } catch (e: InvalidLotteryException) {

                    }
                }
            }
        }
    }

    sealed class UiEvents() {
        data class ShowSnackbar(val message: String) : UiEvents()
        object AddGame : UiEvents()
    }

    private fun getGames() {
        getGamesJob?.cancel()
        getGamesJob = lotteryUseCases.getAllGames().onEach { games ->
            _state.value = state.value.copy(games = games)
        }.launchIn(viewModelScope)
    }
}