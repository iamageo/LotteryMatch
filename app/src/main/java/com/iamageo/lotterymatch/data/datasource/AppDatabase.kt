package com.iamageo.lotterymatch.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iamageo.lotterymatch.domain.model.LotteryGame

@Database(
    entities = [LotteryGame::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appDAO: AppDAO

    companion object {
        const val DATABASE_NAME = "iamageo.lottery_game.db"
    }

}