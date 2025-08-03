package com.mmaquera.happybabystyle.view.login.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmaquera.happybabystyle.ui.theme.HappyBabyStyleTheme

/**
 * Dialog para mostrar errores de login
 * Siguiendo Single Responsibility Principle - solo maneja la visualización de errores
 */
@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    title: String = "Error"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

@Preview(name = "Error Dialog", showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    HappyBabyStyleTheme {
        ErrorDialog(
            message = "Credenciales incorrectas. Por favor verifica tu email y contraseña.",
            onDismiss = {}
        )
    }
}

@Preview(name = "Error Dialog - Network", showBackground = true)
@Composable
private fun ErrorDialogNetworkPreview() {
    HappyBabyStyleTheme {
        ErrorDialog(
            title = "Error de Conexión",
            message = "No se pudo conectar al servidor. Verifica tu conexión a internet.",
            onDismiss = {}
        )
    }
}