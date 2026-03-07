package com.sfluegel.puzzleutils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CellShape = RoundedCornerShape(4.dp)

/**
 * A single interactive cell in a puzzle grid.
 *
 * Handles the selection highlight, border, and click. All visual content
 * (letters, marks, symbols) is provided by the caller via [content].
 *
 * @param cellSize        Width and height of this cell.
 * @param isSelected      Whether this cell is currently selected by the player.
 * @param backgroundColor Background fill; callers compute this from game state.
 * @param onClick         Called when the cell is tapped.
 * @param content         Composable drawn inside the cell, centred.
 */
@Composable
fun PuzzleGridCell(
    cellSize: Dp,
    isSelected: Boolean,
    backgroundColor: Color,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary
                      else            MaterialTheme.colorScheme.outline
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Box(
        modifier = Modifier
            .size(cellSize)
            .padding(2.dp)
            .background(backgroundColor, CellShape)
            .border(borderWidth, borderColor, CellShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content          = content
    )
}

/**
 * A non-interactive hint cell placed around the puzzle grid border.
 *
 * Shows an optional primary hint (bold, prominent) and an optional secondary
 * hint (smaller) stacked vertically inside a cell-sized box.
 *
 * @param primaryHint   Display string for the primary hint, or null to show nothing.
 * @param cellSize      Width and height of this cell.
 * @param secondaryHint Display string for the secondary hint, or null to omit it.
 */
@Composable
fun PuzzleHintCell(
    primaryHint: String?,
    cellSize: Dp,
    secondaryHint: String? = null
) {
    Box(
        modifier         = Modifier.size(cellSize),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (primaryHint != null) {
                Text(
                    text       = primaryHint,
                    fontWeight = FontWeight.Bold,
                    fontSize   = (cellSize.value * 0.40f).sp,
                    color      = MaterialTheme.colorScheme.primary
                )
            }
            if (secondaryHint != null) {
                Text(
                    text     = secondaryHint,
                    fontSize = (cellSize.value * 0.28f).sp,
                    color    = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
