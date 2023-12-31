package com.iamageo.lotterymatch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddGameDialog(onDismiss: () -> Unit, addLotteryViewModel: AddLotteryViewModel) {

    val gameText by addLotteryViewModel.gameText.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar novo jogo") },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OtpTextField(modifier = Modifier.weight(1f).padding(8.dp), otpText = gameText, onOtpTextChange = { newValue, _ ->
                        addLotteryViewModel.onEvent(AddLotteryEvents.EnteredGame(newValue))
                    })
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    addLotteryViewModel.onEvent(AddLotteryEvents.SaveLotteryGame)
                    onDismiss()
                }
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}