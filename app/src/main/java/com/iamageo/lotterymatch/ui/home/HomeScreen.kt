package com.iamageo.lotterymatch.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iamageo.lotterymatch.Screens
import com.iamageo.lotterymatch.domain.model.LotteryGame
import com.iamageo.lotterymatch.ui.add_game.AddGameDialog
import com.iamageo.lotterymatch.ui.add_game.AddLotteryViewModel
import com.iamageo.lotterymatch.ui.components.OtpTextField
import kotlinx.coroutines.launch

fun countMatches(game: LotteryGame, sortedNumbers: Set<Int>): Int {
    val gameNumbers = game.game.split(",").mapNotNull { it.trim().toIntOrNull() }
    return gameNumbers.count { it in sortedNumbers }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    addLotteryViewModel: AddLotteryViewModel = hiltViewModel(),
) {

    var showDialog by remember { mutableStateOf(false) }
    var otpText by remember { mutableStateOf("") }

    val state = homeViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val sortedNumbers = otpText.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
    val filteredGames = state.games.sortedByDescending { game ->
        countMatches(game, sortedNumbers)
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.onSurface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddLotteryGame.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = ""
                )
            }
        },
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Acerto Loteria",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                actions = {

                }
            )
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if(state.games.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Nenhum jogo cadastrado no momento.", color = MaterialTheme.colors.surface)
                }
            }

            if(state.games.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Informe os números sorteados abaixo.",
                        color = MaterialTheme.colors.surface,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(bottom = 8.dp)
                        )
                    OtpTextField(
                        otpText = otpText,
                        onOtpTextChange = { value, _ ->
                            otpText = value
                            val sortedNumbers = value.chunked(2).mapNotNull { it.toIntOrNull() }.toSet()
                            homeViewModel.filterAndSortGames(sortedNumbers)
                        }
                    )

                }
            }

            if(state.games.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(text = state.games.size.toString() + " jogos cadastrados.", color = MaterialTheme.colors.surface)
                }
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()) {
                items(filteredGames) { game ->
                    GameItem(game = game, otpText = otpText, modifier = Modifier
                        .padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(), onDeleteClick = {
                        homeViewModel.onEvent(HomeEvents.DeleteGame(game))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Jogo deletado",
                                actionLabel = "Desfazer?"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                homeViewModel.onEvent(HomeEvents.RestoreGame)
                            }
                        }
                    })
                }
            }
        }
    }

}
