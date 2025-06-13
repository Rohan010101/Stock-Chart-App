package com.example.stockchart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.stockchart.presentation.chart.CompanyChartScreen
import com.example.stockchart.presentation.companyList.CompanyListScreen



//@Composable
//fun NavGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        startDestination = "company_list"
//    ) {
//        composable("company_list") {
//            CompanyListScreen(navController = navController)
//        }
//        composable(
//            route = "chart_screen/{symbol}",
//            arguments = listOf(navArgument("symbol") {
//                type = NavType.StringType
//            })
//        ) {
//            val symbol = it.arguments?.getString("symbol") ?: ""
//            CompanyChartScreen(symbol = symbol)
//        }
//    }
//}








fun NavGraphBuilder.appNavGraph(navController: NavController) {
    composable(route = "company_list"){
        CompanyListScreen(navController)
    }
    composable(
        route = "chat_screen/{company}",
        arguments = listOf(navArgument("company") {
            type = NavType.StringType
        })
    ) {
        CompanyChartScreen(navController)
    }
}