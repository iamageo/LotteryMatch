package com.iamageo.lotterymatch.domain.repository

import com.iamageo.lotterymatch.domain.model.LotteryGame
import kotlinx.coroutines.flow.Flow

interface LotteryRepository {

    fun getAllGames() : Flow<List<LotteryGame>>

    suspend fun getLotteryById(id: Int) : LotteryGame?

    suspend fun insertLotteryGame(game: LotteryGame) : Long

    suspend fun deleteLotteryGame(game: LotteryGame) : Int

}