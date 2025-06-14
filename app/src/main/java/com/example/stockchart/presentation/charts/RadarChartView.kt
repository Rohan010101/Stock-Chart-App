package com.example.stockchart.presentation.charts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry


@Composable
fun RadarChartView(
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
            RadarChart(context).apply {
                description.text = label
                webLineWidth = 1f
                webColor = Color.LightGray.toArgb()
                webColorInner = Color.Gray.toArgb()
            }
        },
        update = { chart ->
            val entries = data.mapIndexed { index, value -> RadarEntry(value) }
            val dataSet = RadarDataSet(entries, label).apply {
                color = lineColor.toArgb()
                fillColor = lineColor.toArgb()
                setDrawFilled(true)
                valueTextColor = textColor.toArgb()
            }

            chart.data = RadarData(dataSet)
            chart.setBackgroundColor(Color.Transparent.toArgb())
            chart.description.textColor = textColor.toArgb()
            chart.legend.textColor = textColor.toArgb()
            chart.yAxis.textColor = textColor.toArgb()
            chart.xAxis.textColor = textColor.toArgb()
            chart.invalidate()
        }
    )
}
