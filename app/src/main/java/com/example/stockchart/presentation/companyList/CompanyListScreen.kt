package com.example.stockchart.presentation.companyList

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.domain.model.Company
import com.example.stockchart.presentation.utils.SelectedCompanyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



@SuppressLint("ContextCastToActivity")
@Composable
fun CompanyListScreen(
//    onCompanyClick: (Company) -> Unit,
    navController: NavController,
    viewModel: CompanyListViewModel = hiltViewModel()
){
    val state = viewModel.state
    val context = LocalContext.current
    val sharedViewModel: SelectedCompanyViewModel = viewModel(LocalContext.current as ComponentActivity)

    // Handle Navigation
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CompanyListEvent.OnCompanyClick -> {
                    sharedViewModel.selectedCompany = event.company
                    navController.navigate("chart_screen")
                }
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Companies",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(state.companies) {company ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            viewModel.onCompanyClicked(company)
//                            onCompanyClick(company)
                        },
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = company.symbol,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}