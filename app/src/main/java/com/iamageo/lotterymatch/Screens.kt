package com.iamageo.lotterymatch

sealed class Screens(val route: String) {
    object LotteryMain : Screens("app_main")

}