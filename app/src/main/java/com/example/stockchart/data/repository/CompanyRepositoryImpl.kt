package com.example.stockchart.data.repository

import android.content.Context
import android.util.Log
import com.example.stockchart.domain.model.Company
import com.example.stockchart.domain.repository.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class CompanyRepositoryImpl(
    private val context: Context
): CompanyRepository {
    override suspend fun getCompanies(): List<Company> = withContext(Dispatchers.IO) {
        val companies = mutableListOf<Company>()
        val inputStream = context.assets.open("dump.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))


        reader.readLine() // skip header

        reader.forEachLine { line ->
            val parts = line.split(",")
            if (parts.size >= 12) {
                val name = parts[0]
                val symbol = parts[1]
                val sector = parts[2]
                val stockValues = parts.subList(3, 8).mapNotNull { it.toFloatOrNull() }
                val changePercent = parts[8]
                val marketCap = parts[9]
                val volume = parts[10]
                val isTrending = parts[11].trim().lowercase() == "true"

                companies.add(
                    Company(
                        name = name,
                        symbol = symbol,
                        sector = sector,
                        stockValues = stockValues,
                        changePercent = changePercent,
                        marketCap = marketCap,
                        volume = volume,
                        isTrending = isTrending
                    )
                )            }
        }
        companies
    }
}