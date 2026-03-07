package com.sfluegel.puzzleutils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A mini grid of candidate marks displayed inside a puzzle cell.
 *
 * All [labels] occupy fixed positional slots so a mark's visual position never
 * shifts as candidates are added or removed. Unmarked slots are left blank;
 * marked slots (by index into [labels]) show their label string.
 *
 * @param labels   Display strings for every candidate slot, in row-major order.
 * @param marked   Indices into [labels] that are currently marked by the player.
 * @param cols     Number of columns in the mini grid.
 * @param cellSize The outer cell's size, used to scale the font proportionally.
 */
@Composable
fun PencilMarksGrid(
    labels: List<String>,
    marked: Set<Int>,
    cols: Int,
    cellSize: Dp
) {
    val numRows  = (labels.size + cols - 1) / cols
    val fontSize = (minOf(cellSize.value / cols, cellSize.value / numRows) * 0.85f).sp

    Column(modifier = Modifier.fillMaxSize().padding(1.dp)) {
        labels.chunked(cols).forEachIndexed { rowIdx, rowLabels ->
            Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                rowLabels.forEachIndexed { colIdx, label ->
                    val absoluteIdx = rowIdx * cols + colIdx
                    Box(
                        modifier         = Modifier.weight(1f).fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (absoluteIdx in marked) {
                            Text(
                                text       = label,
                                fontSize   = fontSize,
                                lineHeight = fontSize,
                                fontWeight = FontWeight.Medium,
                                color      = MaterialTheme.colorScheme.primary,
                                textAlign  = TextAlign.Center
                            )
                        }
                    }
                }
                // Pad the last row if it has fewer items than cols
                repeat(cols - rowLabels.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}
