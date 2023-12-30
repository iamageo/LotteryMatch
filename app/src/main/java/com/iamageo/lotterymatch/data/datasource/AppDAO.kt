package com.iamageo.lotterymatch.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iamageo.lotterymatch.domain.model.LotteryGame
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDAO {

    @Query("SELECT * From lotterygame")
    fun getAllGames() : Flow<List<LotteryGame>>

    @Query("SELECT * FROM lotterygame WHERE id = :id")
    fun getGameById(id: Int) : LotteryGame?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: LotteryGame) : Long

    @Delete
    fun deleteGame(game: LotteryGame) : Int
}