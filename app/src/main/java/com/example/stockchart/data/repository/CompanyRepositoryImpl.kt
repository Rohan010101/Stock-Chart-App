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
        Log.d("CompanyRepositoryImpl", "getCompanies fun started ")
        val companies = mutableListOf<Company>()
        Log.d("CompanyRepositoryImpl", "val companies done ")

        val inputStream = context.assets.open("dump.csv")
        Log.d("CompanyRepositoryImpl", "inputStream done ")

        val reader = BufferedReader(InputStreamReader(inputStream))
        Log.d("CompanyRepositoryImpl", "reader done ")


        reader.readLine() // skip header
        Log.d("CompanyRepositoryImpl", "reader.readLine() done ")

        reader.forEachLine { line ->
            val parts = line.split(",")
            if (parts.size >= 3) {
                val name = parts[0]
                val symbol = parts[1]
                val values = parts.drop(2).mapNotNull { it.toFloatOrNull() }
                companies.add(Company(name, symbol, values))
            }
        }
        companies
    }
}