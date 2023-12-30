package com.iamageo.lotterymatch.domain.usecases

import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.repository.LotteryRepository
import kotlinx.coroutines.flow.Flow

class GetGames(
    private val repository: LotteryRepository
) {
    operator fun invoke(): Flow<List<LotteryGame>> {
        return repository.getAllGames()
    }
}