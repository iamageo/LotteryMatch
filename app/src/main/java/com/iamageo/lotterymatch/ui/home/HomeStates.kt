package com.iamageo.lotterymatch.ui.home

import com.iamageo.lotterymatch.domain.model.LotteryGame

data class HomeStates(
    val games: List<LotteryGame> = emptyList(),
    val isDarkTheme: Boolean = true
)