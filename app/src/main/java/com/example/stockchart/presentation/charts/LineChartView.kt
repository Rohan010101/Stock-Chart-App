package com.example.stockchart.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun LineChartView(
    data: List<Float>,
    lineColor: Color,
    textColor: Color,
    label: String
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            LineChart(context).apply {
                setTouchEnabled(true)
                setPinchZoom(true)
                description.text = label
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, value -> Entry(index.toFloat(), value) }
            val dataSet = LineDataSet(entries, label).apply {
                color = lineColor.toArgb()
                valueTextColor = textColor.toArgb()
                lineWidth = 2f
                setCircleColor(lineColor.toArgb())
                setDrawValues(true)
                setDrawCircles(true)
                circleRadius = 3f
            }

            chart.data = LineData(dataSet)
            chart.setBackgroundColor(Color.Transparent.toArgb())
            chart.description.textColor = textColor.toArgb()
            chart.legend.textColor = textColor.toArgb()
            chart.axisLeft.textColor = textColor.toArgb()
            chart.axisRight.isEnabled = false
            chart.xAxis.textColor = textColor.toArgb()
            chart.xAxis.setDrawGridLines(false)
            chart.invalidate()
        }
    )
}
