package com.example.stockchart.domain.usecase

import com.example.stockchart.domain.model.Company
import com.example.stockchart.domain.repository.CompanyRepository

class GetCompaniesUseCase(
    private val repository: CompanyRepository
) {
    suspend operator fun invoke(): List<Company> = repository.getCompanies()
}