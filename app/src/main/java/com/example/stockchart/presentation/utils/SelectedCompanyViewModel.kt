package com.example.stockchart.presentation.utils

import androidx.lifecycle.ViewModel
import com.example.stockchart.domain.model.Company
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectedCompanyViewModel @Inject constructor(): ViewModel() {
    var selectedCompany: Company? = null
}