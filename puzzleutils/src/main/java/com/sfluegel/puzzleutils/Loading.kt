package com.sfluegel.puzzleutils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ── Wavy loading indicator ────────────────────────────────────────────────────

@Composable
fun WavyLoadingIndicator(dotCount: Int = 5) {
    val transition = rememberInfiniteTransition(label = "wavy")
    val color = MaterialTheme.colorScheme.primary
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(40.dp)
    ) {
        repeat(dotCount) { index ->
            val offsetY by transition.animateFloat(
                initialValue = 0f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 900
                        0f at 0 using FastOutSlowInEasing
                        -18f at 250 using FastOutSlowInEasing
                        0f at 500
                        // stays at 0 from 500–900 (rest between bounces)
                    },
                    repeatMode = RepeatMode.Restart,
                    initialStartOffset = StartOffset(index * 120)
                ),
                label = "dot_$index"
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .offset(y = offsetY.dp)
                    .background(color, CircleShape)
            )
        }
    }
}