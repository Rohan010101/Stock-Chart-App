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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.presentation.charts.BarChartView
import com.example.stockchart.presentation.charts.LineChartView
import com.example.stockchart.presentation.charts.PieChartView
import com.example.stockchart.presentation.charts.RadarChartView
import com.example.stockchart.presentation.theme.CardColors
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
    val primaryColor = MaterialTheme.colorScheme.primary

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
                .padding(10.dp)
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

                        // 1. Sector
                        InfoCard(
                            title = "Sector",
                            value = company.sector,
                            backgroundColor = CardColors.InfoColors[0],
                        )

                        // 2. Market Capital
                        InfoCard(
                            title = "Market Cap",
                            value = company.marketCap,
                            backgroundColor = CardColors.InfoColors[1],
                        )                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // 3. Volume
                        InfoCard(
                            title = "Volume",
                            value = company.volume,
                            backgroundColor = CardColors.InfoColors[2],
                        )

                        // 4. Change
                        InfoCard(
                            title = "Change",
                            value = company.changePercent,
                            backgroundColor = CardColors.InfoColors[3],
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Chart 1: Line Chart
            item {
                ChartCard("📈 Price Trend", CardColors.ChartColors[0]) {
                    LineChartView(
                        data = company.stockValues,
                        lineColor = primaryColor,
                        label = "Stock Price"
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Chart 2: Bar Chart
            item {
                ChartCard("📊 Price Bars", CardColors.ChartColors[1]) {
                    BarChartView(
                        data = company.stockValues,
                        barColor = primaryColor,
                        label = "Prices as Bars"
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Chart 3: Pie Chart
            item {
                ChartCard("🧭 Trending Status", CardColors.ChartColors[2]) {
                    PieChartView(
                        isTrending = company.isTrending,
                        highlightColor = primaryColor,
                        label = "Is Trending"
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Chart 4: Radar Chart
            item {
                ChartCard("📡 Performance Overview", CardColors.ChartColors[3]) {
                    RadarChartView(
                        data = company.stockValues,
                        lineColor = primaryColor,
                        label = "Radar View"
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }
}