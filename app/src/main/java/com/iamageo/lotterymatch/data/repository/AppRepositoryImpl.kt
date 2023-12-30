package com.iamageo.lotterymatch.data.repository

import com.iamageo.lotterymatch.data.datasource.AppDAO
import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.domain.repository.LotteryRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val appDAO: AppDAO
) : LotteryRepository {
    override fun getAllGames(): Flow<List<LotteryGame>> {
        return appDAO.getAllGames()
    }

    override suspend fun getLotteryById(id: Int): LotteryGame? {
        return appDAO.getGameById(id)
    }

    override suspend fun insertLotteryGame(game: LotteryGame): Long {
        return appDAO.insertGame(game = game)
    }

    override suspend fun deleteLotteryGame(game: LotteryGame) : Int {
        return appDAO.deleteGame(game = game)
    }

}