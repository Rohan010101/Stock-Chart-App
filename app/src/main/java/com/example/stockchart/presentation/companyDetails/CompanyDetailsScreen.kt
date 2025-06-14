package com.example.stockchart.presentation.companyDetails

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.presentation.charts.BarChartView
import com.example.stockchart.presentation.charts.LineChartView
import com.example.stockchart.presentation.charts.PieChartView
import com.example.stockchart.presentation.charts.RadarChartView
import com.example.stockchart.presentation.utils.ChartCard
import com.example.stockchart.presentation.utils.InfoCard
import com.example.stockchart.presentation.utils.SelectedCompanyViewModel

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
    val cardBackground = MaterialTheme.colorScheme.surfaceVariant
    val chartTextColor = MaterialTheme.colorScheme.onSurface

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


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                // 1. Info Cards
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoCard("Sector", company.sector, cardBackground, chartTextColor)
                        InfoCard("Market Cap", company.marketCap, cardBackground, chartTextColor)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        InfoCard("Volume", company.volume, cardBackground, chartTextColor)
                        InfoCard("Change", company.changePercent, cardBackground, chartTextColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Chart 1: Line Chart - Price Trend
            item {
                ChartCard("📈 Price Trend", cardBackground) {
                    LineChartView(company.stockValues, primaryColor, chartTextColor, "Stock Price")
                }
            }

            // Chart 2: Bar Chart - Price Bars
            item {
                ChartCard("📊 Price Bars", cardBackground) {
                    BarChartView(
                        company.stockValues,
                        primaryColor,
                        chartTextColor,
                        "Prices as Bars"
                    )
                }
            }

            // Chart 3: Pie Chart - Trending Status
            item {
                ChartCard("🧭 Trending Status", cardBackground) {
                    PieChartView(company.isTrending, primaryColor, chartTextColor, "Is Trending")
                }
            }

            // Chart 4: Radar Chart - Performance Overview
            item {
                ChartCard("📡 Performance Overview", cardBackground) {
                    RadarChartView(company.stockValues, primaryColor, chartTextColor, "Radar View")
                }
            }
        }
    }
}


//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//
//            // 1. Info Cards
//            Column(
//                modifier = Modifier,
//                verticalArrangement = Arrangement.SpaceEvenly
//            ){
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    InfoCard("Sector", company.sector, )
//                    InfoCard("Market Cap", company.marketCap)
//                }
//
//                Spacer(modifier = Modifier.height(14.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    InfoCard("Volume", company.volume)
//                    InfoCard("Change", company.changePercent)
//                }
//            }
//
//            Spacer(Modifier.height(24.dp))
//
//            // 2. Swipable Charts
//            val chartTypes = listOf("Line", "Bar", "Pie", "Radar")
//            val pagerstate = rememberPagerState(pageCount = { chartTypes.size })
//
//            HorizontalPager(state = pagerstate) { page ->
//                when (page) {
//                    0 -> LineChartView(company.stockValues, "Price Trend", primaryColor, textColor)
//                    1 -> BarChartView(company.stockValues, "Price as Bars", primaryColor, textColor)
//                    2 -> PieChartView(company.isTrending, "Trending Status", primaryColor, textColor)
//                    3 -> RadarChartView(company.stockValues, "Performance Radar", primaryColor, textColor)
//                }
//            }


//            // Optional indicator
//            HorizontalPagerIndicator(pagerState = pagerstate, modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(8.dp))
//        }
//
//        }


//        Column(
//            modifier = Modifier
//            .fillMaxSize()
//            .padding(padding)
//            .padding(16.dp)
//        ) {
//
//            Text("Sector: ${company.sector}", style = MaterialTheme.typography.bodyMedium)
//            Text("Market Cap: ${company.marketCap}", style = MaterialTheme.typography.bodyMedium)
//            Text("Volume: ${company.volume}", style = MaterialTheme.typography.bodyMedium)
//            Text("Change: ${company.changePercent}", style = MaterialTheme.typography.bodyMedium)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 1. Stock Prices Chart
//            AndroidView(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp),
//                factory = { context ->
//                    LineChart(context).apply {
//                        setTouchEnabled(true)
//                        description.text = "Stock Prices"
//                    }
//                },
//                update = { chart ->
//                    val entries = company.stockValues.mapIndexed { i, v -> Entry(i.toFloat(), v) }
//                    val dataSet = LineDataSet(entries, "Price").apply {
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
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // 2. Volume Chart (mocking volume values as fixed for now)
//            AndroidView(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(250.dp),
//                factory = { context ->
//                    LineChart(context).apply {
//                        setTouchEnabled(true)
//                        description.text = "Volume (mock)"
//                    }
//                },
//                update = { chart ->
//                    val volumes = listOf(10f, 20f, 15f, 25f, 30f) // Replace with real logic if available
//                    val entries = volumes.mapIndexed { i, v -> Entry(i.toFloat(), v) }
//                    val dataSet = LineDataSet(entries, "Volume").apply {
//                        color = secondary.toArgb()
//                        valueTextColor = textValueColor.toArgb()
//                        lineWidth = 2f
//                        setCircleColor(primaryColor.toArgb())
//                        setDrawValues(true)
//                        setDrawCircles(true)
//                        circleRadius = 3f
//                    }
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
//}