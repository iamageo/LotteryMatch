package com.iamageo.lotterymatch.domain.usecases

data class LotteryUseCases(
    val getAllGames: GetGames,
    val deleteGame: DeleteGame,
    val addGame: AddGame,
    val getGameById: GetGameById
)