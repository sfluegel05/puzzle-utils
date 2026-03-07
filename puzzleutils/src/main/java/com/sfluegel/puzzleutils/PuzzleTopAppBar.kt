package com.sfluegel.puzzleutils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Standard top app bar for a puzzle game screen.
 *
 * Displays a back button, title, live elapsed-time timer, and optional reset / help actions.
 * Help and reset-confirmation dialogs are managed internally; callers only supply text and callbacks.
 *
 * @param title              Text shown in the title slot.
 * @param onBack             Called when the navigation (back) icon is tapped.
 * @param elapsedSeconds     Seconds to display in the timer (top-right).
 * @param onReset            When non-null a reset button (↺) is shown; this callback is invoked
 *                           after the user confirms (if [resetConfirmTitle]/[resetConfirmText]
 *                           are provided) or immediately otherwise.
 * @param resetConfirmTitle  Optional title for the reset confirmation dialog.
 * @param resetConfirmText   Optional body for the reset confirmation dialog.
 * @param helpTitle          Optional title for the help dialog; help button hidden when null.
 * @param helpText           Optional body for the help dialog; help button hidden when null.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleTopAppBar(
    title: String,
    onBack: () -> Unit,
    elapsedSeconds: Long,
    onReset: (() -> Unit)? = null,
    resetConfirmTitle: String? = null,
    resetConfirmText: String? = null,
    helpTitle: String? = null,
    helpText: String? = null,
) {
    var showHelp         by remember { mutableStateOf(false) }
    var showResetConfirm by remember { mutableStateOf(false) }
    val hasConfirmDialog = resetConfirmTitle != null || resetConfirmText != null

    if (showHelp && helpText != null) {
        AlertDialog(
            onDismissRequest = { showHelp = false },
            title            = helpTitle?.let { { Text(it) } },
            text             = { Text(helpText) },
            confirmButton    = {
                TextButton(onClick = { showHelp = false }) { Text("Got it") }
            }
        )
    }

    if (showResetConfirm && onReset != null) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title            = resetConfirmTitle?.let { { Text(it) } },
            text             = resetConfirmText?.let { { Text(it) } },
            confirmButton    = {
                TextButton(onClick = { onReset(); showResetConfirm = false }) { Text("Reset") }
            },
            dismissButton    = {
                TextButton(onClick = { showResetConfirm = false }) { Text("Cancel") }
            }
        )
    }

    TopAppBar(
        title           = { Text(title) },
        navigationIcon  = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (onReset != null) {
                IconButton(onClick = { if (hasConfirmDialog) showResetConfirm = true else onReset() }) {
                    Text(
                        text  = "↺",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (helpText != null) {
                IconButton(onClick = { showHelp = true }) {
                    Text(
                        text  = "?",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Text(
                text     = "%d:%02d".format(elapsedSeconds / 60, elapsedSeconds % 60),
                style    = MaterialTheme.typography.titleMedium,
                color    = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    )
}
