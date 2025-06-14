package com.example.stockchart.presentation.companyList

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.stockchart.presentation.utils.SelectedCompanyViewModel
import com.example.stockchart.presentation.utils.Sparkline
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun CompanyListScreen(
    navController: NavController,
    viewModel: CompanyListViewModel = hiltViewModel()
){
    val state = viewModel.state
    val context = LocalContext.current
    val sharedViewModel: SelectedCompanyViewModel = viewModel(context as ComponentActivity)
    val focusManager = LocalFocusManager.current
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)


    // Handle Navigation
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CompanyListEvent.OnCompanyClick -> {
                    focusManager.clearFocus()
                    sharedViewModel.selectedCompany = event.company
                    navController.navigate("chart_screen")
                }
            }
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 0.dp),
                title = {
                    Text(
                        text = "Stock Chart",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            )
        }
    ) { padding ->

        AnimatedVisibility(visible = state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {

            OutlinedTextField(
                value = viewModel.searchQuery.collectAsState().value,
                onValueChange = viewModel::onSearchQueryChange,
                label = { Text("Search companies...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(32.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.refreshCompanies() }
            ) {
                LazyColumn {
                    items(state.companies) { company ->

                        val priceChange = company.stockValues.last() - company.stockValues.first()
                        val isUp = priceChange >= 0

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable(
                                    onClick = { viewModel.onCompanyClicked(company) },
                                    indication = LocalIndication.current,
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = company.name,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Text(
                                        text = company.symbol,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Sparkline(company.stockValues)
                                    Text(
                                        text = if (isUp) "↑" else "↓",
                                        color = if (isUp) Color(0xFF4CAF50) else Color(0xFFF44336),
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = 25.sp
                                        )
                                    )
                                }


                            }
                        }
                    }

                    if (state.companies.isEmpty() && !state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No companies found.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
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
}