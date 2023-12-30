package com.iamageo.lotterymatch.di

import android.app.Application
import androidx.room.Room
import com.iamageo.lotterymatch.data.datasource.AppDatabase
import com.iamageo.lotterymatch.data.repository.AppRepositoryImpl
import com.iamageo.lotterymatch.domain.repository.LotteryRepository
import com.iamageo.lotterymatch.domain.usecases.AddGame
import com.iamageo.lotterymatch.domain.usecases.DeleteGame
import com.iamageo.lotterymatch.domain.usecases.GetGameById
import com.iamageo.lotterymatch.domain.usecases.GetGames
import com.iamageo.lotterymatch.domain.usecases.LotteryUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppRepository(db: AppDatabase): LotteryRepository {
        return AppRepositoryImpl(db.appDAO)
    }

    @Provides
    @Singleton
    fun provideAppUseCases(repository: LotteryRepository): LotteryUseCases {
        return LotteryUseCases(
            getAllGames = GetGames(repository),
            deleteGame = DeleteGame(repository),
            addGame = AddGame(repository),
            getGameById = GetGameById(repository)
        )
    }

}