package com.example.stockchart.domain.repository

import com.example.stockchart.domain.model.Company

interface CompanyRepository {
    suspend fun getCompanies(): List<Company>
}