package com.example.stockchart.presentation.companyList

import com.example.stockchart.domain.model.Company

sealed class CompanyListEvent {
    data class OnCompanyClick(val company: Company): CompanyListEvent()
}