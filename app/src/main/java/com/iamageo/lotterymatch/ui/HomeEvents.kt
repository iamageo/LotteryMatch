package com.iamageo.lotterymatch.ui

import com.iamageo.lotterymatch.domain.model.LotteryGame

sealed class HomeEvents {

    object RestoreGame : HomeEvents()
    data class DeleteGame(val game: LotteryGame) : HomeEvents()

    data class AddGame(val game: LotteryGame): HomeEvents()

    object ChangeTheme : HomeEvents()
}