package com.example.stockchart.presentation.chart

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.domain.model.Company
import com.example.stockchart.presentation.utils.SelectedCompanyViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

@SuppressLint("ContextCastToActivity")
@Composable
fun CompanyChartScreen(
//    company: Company,
    navController: NavController,
    viewModel: SelectedCompanyViewModel = hiltViewModel()
) {

    val viewModel: SelectedCompanyViewModel = viewModel(LocalContext.current as ComponentActivity)
    val company = viewModel.selectedCompany ?: return

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = company.name,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AndroidView(
            modifier = Modifier.fillMaxWidth().height(300.dp),
            factory = { context ->
                LineChart(context).apply {
                    setTouchEnabled(true)
                    setPinchZoom(true)
                    description.text = "${company.symbol} stock prices"
                }
            },
            update = { chart ->
                val entries = company.stockValues.mapIndexed { index, price ->
                    Entry(index.toFloat(), price)
                }
                val dataSet = LineDataSet(entries, company.symbol).apply {
                    color = ColorTemplate.COLORFUL_COLORS[0]
                    valueTextColor = Color.BLACK
                    lineWidth = 2f
                }
                chart.data = LineData(dataSet)
                chart.invalidate()
            }
        )
    }
}