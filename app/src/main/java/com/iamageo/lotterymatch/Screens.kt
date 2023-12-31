package com.iamageo.lotterymatch

sealed class Screens(val route: String) {
    object LotteryMain : Screens("app_main_screen")
    object AddLotteryGame : Screens("add_lottery_game_screen")

}