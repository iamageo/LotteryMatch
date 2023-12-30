package com.iamageo.lotterymatch.domain.usecases

import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.repository.LotteryRepository


class DeleteGame(
    private val repository: LotteryRepository
) {

    suspend operator fun invoke(game: LotteryGame) {
        repository.deleteLotteryGame(game)
    }
}