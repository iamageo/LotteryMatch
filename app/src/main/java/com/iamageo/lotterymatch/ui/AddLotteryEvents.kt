package com.iamageo.lotterymatch.ui

sealed class AddLotteryEvents {
    data class EnteredGame(val value: String) : AddLotteryEvents()
    object SaveLotteryGame : AddLotteryEvents()
}