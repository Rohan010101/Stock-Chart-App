package com.example.stockchart.presentation.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.stockchart.domain.model.Company
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

//@HiltViewModel
//class CompanyChartViewModel @Inject constructor(
//    savedStateHandle: SavedStateHandle
//) : ViewModel() {
//    private val companyJson = savedStateHandle.get<String>("company")
//    val state: CompanyChartState
//
//    init {
//        val decoded = Json.decodeFromString<Company>(companyJson ?: "")
//        state = CompanyChartState(company = decoded)
//    }
//}
