package com.iamageo.lotterymatch.ui.add_game

sealed class AddLotteryEvents {
    data class EnteredGame(val value: String) : AddLotteryEvents()
    object SaveLotteryGame : AddLotteryEvents()
}