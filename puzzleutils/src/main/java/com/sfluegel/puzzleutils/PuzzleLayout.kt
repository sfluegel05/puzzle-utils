package com.sfluegel.puzzleutils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Orientation-aware two-panel layout for grid puzzles.
 *
 * - Portrait: grid stacked above controls, both centred.
 * - Landscape: grid on the left, controls on the right, each in a half-width panel.
 *
 * @param grid     Composable for the puzzle grid. Receives the available width and
 *                 height of its panel so the caller can compute an appropriate cell size.
 * @param controls Composable for the input controls shown next to / below the grid.
 */
@Composable
fun PuzzleLayout(
    modifier: Modifier = Modifier,
    grid: @Composable (availableWidth: Dp, availableHeight: Dp) -> Unit,
    controls: @Composable () -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize().padding(8.dp)) {
        val isLandscape   = maxWidth > maxHeight
        val totalWidth    = maxWidth
        val totalHeight   = maxHeight

        if (isLandscape) {
            Row(
                modifier              = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.Top
            ) {
                Box(
                    modifier         = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    grid(totalWidth / 2, totalHeight)
                }
                Box(
                    modifier         = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    controls()
                }
            }
        } else {
            Column(
                modifier            = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                grid(totalWidth, totalHeight)
                Spacer(Modifier.height(16.dp))
                controls()
            }
        }
    }
}
