package com.iamageo.lotterymatch.ui.add_game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iamageo.lotterymatch.ui.components.OtpTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddGameScreen(
    navController: NavController,
    addLotteryViewModel: AddLotteryViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val gameText by addLotteryViewModel.gameText.collectAsState()

    LaunchedEffect(key1 = true) {
        addLotteryViewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddLotteryViewModel.UiEvents.ShowSnackbar -> {
                }
                is AddLotteryViewModel.UiEvents.SaveLotteryGame -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.onSurface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    addLotteryViewModel.onEvent(AddLotteryEvents.SaveLotteryGame)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = ""
                )
            }
        },
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "adicionar novo jogo",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h4
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = MaterialTheme.colors.onSurface,
                        contentDescription = ""
                    )
                }
            )
        },
    ) { padding ->

        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Informe os números do jogo abaixo.",
                color = MaterialTheme.colors.surface,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OtpTextField(modifier = Modifier, otpText = gameText, onOtpTextChange = { newValue, _ ->
                    addLotteryViewModel.onEvent(AddLotteryEvents.EnteredGame(newValue))
                })
            }
        }

    }

}