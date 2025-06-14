package com.example.stockchart.presentation.theme

import androidx.compose.ui.graphics.Color

object CardColors {
    val InfoColors = listOf(
        Color(0xFFD1C4E9), // Lavender for Sector
        Color(0xFFFFCCBC), // Peach for Market Cap
        Color(0xFFB2DFDB), // Teal for Volume
        Color(0xFFFFF9C4)  // Yellow for Change %
    )

    val ChartColors = listOf(
        Color(0xFFE1F5FE), // Light Blue for Line Chart
        Color(0xFFFFF3E0), // Light Orange for Bar Chart
        Color(0xFFF3E5F5), // Light Purple for Pie Chart
        Color(0xFFE0F2F1)  // Mint for Radar Chart
    )
}
