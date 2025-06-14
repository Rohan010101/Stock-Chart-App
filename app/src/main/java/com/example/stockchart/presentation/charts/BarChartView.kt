package com.example.stockchart.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


@Composable
fun BarChartView(
    data: List<Float>,
    barColor: Color,
    label: String
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            BarChart(context).apply {
                description.text = label
                setTouchEnabled(true)
                setPinchZoom(true)
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, value -> BarEntry(index.toFloat(), value) }
            val dataSet = BarDataSet(entries, label).apply {
                color = barColor.toArgb()
                valueTextColor = Color.Black.toArgb()
            }

            chart.data = BarData(dataSet)
            chart.setBackgroundColor(Color.Transparent.toArgb())
            chart.description.textColor = Color.Black.toArgb()
            chart.legend.textColor = Color.Black.toArgb()
            chart.axisLeft.textColor = Color.Black.toArgb()
            chart.axisRight.isEnabled = false
            chart.xAxis.textColor = Color.Black.toArgb()
            chart.xAxis.setDrawGridLines(false)
            chart.invalidate()
        }
    )
}
