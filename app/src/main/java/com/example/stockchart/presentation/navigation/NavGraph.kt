package com.example.stockchart.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.stockchart.presentation.companyDetails.CompanyChartScreen
import com.example.stockchart.presentation.companyList.CompanyListScreen


fun NavGraphBuilder.appNavGraph(navController: NavController) {
    composable(route = "company_list") {
        CompanyListScreen(navController)
    }
    composable(route = "chart_screen") {
        CompanyChartScreen(navController)
    }
}