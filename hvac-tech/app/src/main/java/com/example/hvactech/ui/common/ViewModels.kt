package com.example.hvactech.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hvactech.core.ServiceLocator
import com.example.hvactech.domain.CalculatorRepository
import com.example.hvactech.domain.ComparisonRepository
import kotlinx.coroutines.launch

class CalculatorViewModel : ViewModel() {
    private val repo = ServiceLocator.calculatorRepository
    private val _result = MutableLiveData<CalculatorRepository.Result?>()
    val result: LiveData<CalculatorRepository.Result?> = _result

    fun calculate(input: CalculatorRepository.Input) {
        viewModelScope.launch {
            _result.postValue(repo.calculate(input))
        }
    }
}

class ComparisonViewModel : ViewModel() {
    private val repo = ServiceLocator.comparisonRepository
    private val _result = MutableLiveData<ComparisonRepository.Result?>()
    val result: LiveData<ComparisonRepository.Result?> = _result

    fun compare(input: ComparisonRepository.Input) {
        viewModelScope.launch {
            _result.postValue(repo.compare(input))
        }
    }
}

