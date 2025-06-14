package com.example.stockchart.presentation.companyList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockchart.domain.model.Company
import com.example.stockchart.domain.repository.CompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val repository: CompanyRepository
): ViewModel() {

    var state by mutableStateOf(CompanyListState())
        private set

    // Hot Flow + One Time Event
    private val _eventFlow = MutableSharedFlow<CompanyListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allCompanies: List<Company> = emptyList()

    init {
        loadCompanies()
    }

    private fun loadCompanies() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            allCompanies = repository.getCompanies()
            state = state.copy(
                companies = allCompanies,
                isLoading = false
            )
        }
    }

    fun refreshCompanies() {
        loadCompanies()
    }


    fun onCompanyClicked(company: Company) {
        viewModelScope.launch {
            _eventFlow.emit(CompanyListEvent.OnCompanyClick(company))
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterCompanies()
    }

    private fun filterCompanies() {
        val query = _searchQuery.value.trim().lowercase()

        val filtered = if (query.isBlank()) {
            allCompanies
        } else {
            allCompanies.filter { company ->
                company.name.lowercase().contains(query) ||
                        company.symbol.lowercase().contains(query)
            }
        }
        state = state.copy(companies = filtered)
    }
}