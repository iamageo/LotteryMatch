package com.iamageo.lotterymatch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LotteryGame(
    val game: String,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
)

class InvalidLotteryException(message: String): Exception(message)