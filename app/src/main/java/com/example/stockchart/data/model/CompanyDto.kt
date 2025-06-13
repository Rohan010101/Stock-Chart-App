package com.example.stockchart.data.model

data class CompanyDto(
    val name: String,
    val symbol: String,
    val stockValues: List<Float>
)
