package com.iamageo.lotterymatch.domain.usecases

import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.repository.LotteryRepository

class GetGameById(
    private val repository: LotteryRepository
) {

    suspend operator fun invoke(id: Int): LotteryGame? {
        return repository.getLotteryById(id)
    }
}