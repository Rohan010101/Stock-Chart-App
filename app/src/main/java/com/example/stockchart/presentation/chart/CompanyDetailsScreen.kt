package com.example.stockchart.presentation.chart

import android.annotation.SuppressLint
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.presentation.utils.SelectedCompanyViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun CompanyChartScreen(
    navController: NavController,
) {

    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary
    val textValueColor = MaterialTheme.colorScheme.onPrimary
    val secondary = MaterialTheme.colorScheme.secondary
    val onSecondary = MaterialTheme.colorScheme.onSecondary

    val pagerState = rememberPagerState(pageCount = { 2 }) // Update to number of charts
    val scope = rememberCoroutineScope()


    val viewModel: SelectedCompanyViewModel = viewModel(LocalContext.current as ComponentActivity)
    val company = viewModel.selectedCompany ?: return

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = { Text(company.name) },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .padding(8.dp)
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {

            Text("Sector: ${company.sector}", style = MaterialTheme.typography.bodyMedium)
            Text("Market Cap: ${company.marketCap}", style = MaterialTheme.typography.bodyMedium)
            Text("Volume: ${company.volume}", style = MaterialTheme.typography.bodyMedium)
            Text("Change: ${company.changePercent}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Stock Prices Chart
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                factory = { context ->
                    LineChart(context).apply {
                        setTouchEnabled(true)
                        description.text = "Stock Prices"
                    }
                },
                update = { chart ->
                    val entries = company.stockValues.mapIndexed { i, v -> Entry(i.toFloat(), v) }
                    val dataSet = LineDataSet(entries, "Price").apply {
                        color = primaryColor.toArgb()
                        valueTextColor = textValueColor.toArgb()
                        lineWidth = 2f
                        setCircleColor(primaryColor.toArgb())
                        setDrawValues(true)
                        setDrawCircles(true)
                        circleRadius = 3f
                    }

                    chart.setBackgroundColor(backgroundColor.toArgb())
                    chart.description.text = "${company.symbol} stock prices"
                    chart.description.textColor = textColor.toArgb()
                    chart.legend.textColor = textColor.toArgb()
                    chart.axisLeft.textColor = textColor.toArgb()
                    chart.axisRight.isEnabled = false
                    chart.xAxis.textColor = textColor.toArgb()
                    chart.xAxis.setDrawGridLines(false)

                    chart.data = LineData(dataSet)
                    chart.invalidate()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Volume Chart (mocking volume values as fixed for now)
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                factory = { context ->
                    LineChart(context).apply {
                        setTouchEnabled(true)
                        description.text = "Volume (mock)"
                    }
                },
                update = { chart ->
                    val volumes = listOf(10f, 20f, 15f, 25f, 30f) // Replace with real logic if available
                    val entries = volumes.mapIndexed { i, v -> Entry(i.toFloat(), v) }
                    val dataSet = LineDataSet(entries, "Volume").apply {
                        color = secondary.toArgb()
                        valueTextColor = textValueColor.toArgb()
                        lineWidth = 2f
                        setCircleColor(primaryColor.toArgb())
                        setDrawValues(true)
                        setDrawCircles(true)
                        circleRadius = 3f
                    }
                    chart.setBackgroundColor(backgroundColor.toArgb())
                    chart.description.text = "${company.symbol} stock prices"
                    chart.description.textColor = textColor.toArgb()
                    chart.legend.textColor = textColor.toArgb()
                    chart.axisLeft.textColor = textColor.toArgb()
                    chart.axisRight.isEnabled = false
                    chart.xAxis.textColor = textColor.toArgb()
                    chart.xAxis.setDrawGridLines(false)

                    chart.data = LineData(dataSet)
                    chart.invalidate()
                }
            )
        }





//            AndroidView(
//                modifier = Modifier.fillMaxWidth().height(300.dp),
//                factory = { context ->
//                    LineChart(context).apply {
//                        setTouchEnabled(true)
//                        setPinchZoom(true)
//                        description.text = "${company.symbol} stock prices"
//                    }
//                },
//                update = { chart ->
//                    val entries = company.stockValues.mapIndexed { index, price ->
//                        Entry(index.toFloat(), price)
//                    }
//                    val dataSet = LineDataSet(entries, company.symbol).apply {
//                        color = primaryColor.toArgb()
//                        valueTextColor = textValueColor.toArgb()
//                        lineWidth = 2f
//                        setCircleColor(primaryColor.toArgb())
//                        setDrawValues(true)
//                        setDrawCircles(true)
//                        circleRadius = 3f
//                    }
//
//                    chart.setBackgroundColor(backgroundColor.toArgb())
//                    chart.description.text = "${company.symbol} stock prices"
//                    chart.description.textColor = textColor.toArgb()
//                    chart.legend.textColor = textColor.toArgb()
//                    chart.axisLeft.textColor = textColor.toArgb()
//                    chart.axisRight.isEnabled = false
//                    chart.xAxis.textColor = textColor.toArgb()
//                    chart.xAxis.setDrawGridLines(false)
//
//                    chart.data = LineData(dataSet)
//                    chart.invalidate()
//                }
//            )
//        }
    }
}