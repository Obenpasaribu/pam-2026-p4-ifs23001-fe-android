package org.delcom.pam_p4_ifs23001.helper

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

enum class AlertType(val title: String) {
    ERROR("Error"),
    SUCCESS("Success"),
    INFO("Info"),
    WARNING("Warning")
}

data class AlertState(
    val isVisible: Boolean = false,
    val type: AlertType = AlertType.INFO,
    val message: String = ""
)

object AlertHelper {

    fun show(
        currentState: MutableState<AlertState>,
        type: AlertType,
        message: String
    ) {
        currentState.value = AlertState(
            isVisible = true,
            type = type,
            message = message
        )
    }

    fun dismiss(currentState: MutableState<AlertState>) {
        currentState.value = currentState.value.copy(isVisible = false)
    }

    @Composable
    fun ShowAlert(state: MutableState<AlertState>) {
        if (state.value.isVisible) {
            AlertDialog(
                onDismissRequest = { dismiss(state) },
                title = { Text(state.value.type.title) },
                text = { Text(state.value.message) },
                confirmButton = {
                    TextButton(onClick = { dismiss(state) }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}