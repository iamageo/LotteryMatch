package com.iamageo.lotterymatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iamageo.lotterymatch.theme.LotteryTheme
import com.iamageo.lotterymatch.ui.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LotteryMatchMain: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LotteryTheme(darkTheme = true) {

                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screens.LotteryMain.route
                    ) {
                        composable(route = Screens.LotteryMain.route) {
                            HomeScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}