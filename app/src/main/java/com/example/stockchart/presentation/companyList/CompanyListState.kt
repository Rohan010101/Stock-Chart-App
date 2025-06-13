package com.example.stockchart.presentation.companyList

import com.example.stockchart.domain.model.Company

data class CompanyListState(
    val companies: List<Company> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
