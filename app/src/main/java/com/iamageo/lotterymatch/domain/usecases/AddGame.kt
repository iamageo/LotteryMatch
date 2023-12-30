package com.iamageo.lotterymatch.domain.usecases

import com.iamageo.lotterymatch.domain.model.InvalidLotteryException
import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.repository.LotteryRepository

class AddGame(
    private val repository: LotteryRepository
) {
    @Throws(InvalidLotteryException::class)
    suspend operator fun invoke(game: LotteryGame) {
        if (game.game.isBlank()) {
            throw InvalidLotteryException("The title of the nottye can't be empty.")
        }
        repository.insertLotteryGame(game)
    }
}