package com.example.stockchart.presentation.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun Sparkline(stockValues: List<Float>) {

    val primary = MaterialTheme.colorScheme.primary

    Canvas(modifier = Modifier.padding(16.dp).size (60.dp, 30.dp)) {
        val max = stockValues.maxOrNull() ?: 0f
        val min = stockValues.minOrNull() ?: 0f
        val range = max - min
        val step = size.width / (stockValues.size - 1)

        val points = stockValues.mapIndexed { index, value ->
            Offset(
                x = step * index,
                y = size.height - ((value - min) / range * size.height)
            )
        }

        drawPath(
            path = Path().apply {
                moveTo(points[0].x, points[0].y)
                points.drop(1).forEach { lineTo(it.x, it.y) }
            },
            color = primary,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
