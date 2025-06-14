package com.example.stockchart.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


@Composable
fun PieChartView(
    isTrending: Boolean,
    highlightColor: Color,
    label: String
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            PieChart(context).apply {
                description.text = label
                setUsePercentValues(true)
            }
        },
        update = { chart ->
            val entries = listOf(
                PieEntry(if (isTrending) 1f else 0f, "Trending"),
                PieEntry(if (isTrending) 0f else 1f, "Not Trending")
            )

            val dataSet = PieDataSet(entries, "").apply {
                colors = listOf(
                    highlightColor.toArgb(),
                    Color.Gray.toArgb()
                )
                valueTextColor = Color.Black.toArgb()
            }

            chart.data = PieData(dataSet)
            chart.setBackgroundColor(Color.Transparent.toArgb())
            chart.description.textColor = Color.Black.toArgb()
            chart.legend.textColor = Color.Black.toArgb()
            chart.invalidate()
        }
    )
}
