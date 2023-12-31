package com.iamageo.lotterymatch.ui.add_game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamageo.lotterymatch.domain.model.InvalidLotteryException
import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.usecases.LotteryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLotteryViewModel @Inject constructor(
    private val lotteryUseCases: LotteryUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    private val _gameText = MutableStateFlow("")
    val gameText: StateFlow<String> = _gameText.asStateFlow()

    fun onEvent(event: AddLotteryEvents) {
        when (event) {
            is AddLotteryEvents.EnteredGame -> {
                _gameText.value = event.value
            }
            is AddLotteryEvents.SaveLotteryGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        lotteryUseCases.addGame(
                            LotteryGame(
                                game = formatTextWithComma(gameText.value),
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvents.SaveLotteryGame)
                    } catch (e: InvalidLotteryException) {
                        e.message?.let { UiEvents.ShowSnackbar(it) }?.let { _eventFlow.emit(it) }
                    }
                }
            }
        }
    }

    sealed class UiEvents() {
        data class ShowSnackbar(val message: String) : UiEvents()
        object SaveLotteryGame : UiEvents()
    }

    fun formatTextWithComma(text: String): String {
        return text.filter { it.isDigit() }
            .chunked(2)
            .joinToString(",")
    }

}