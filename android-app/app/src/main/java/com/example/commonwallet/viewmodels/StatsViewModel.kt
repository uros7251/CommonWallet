package com.example.commonwallet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commonwallet.data.repository.WalletRepository
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(private val walletRepository: WalletRepository)
    :ViewModel()
{
    private val _statistics = MutableLiveData<List<TotalAndNetPaymentStat>>()
    val statistics: LiveData<List<TotalAndNetPaymentStat>> = _statistics

    private val _latestPayments = MutableLiveData<List<Payment>>()
    val latestPayments: LiveData<List<Payment>> = _latestPayments

    init {
        loadStats()
        loadPayments()
    }

    private fun loadStats(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _statistics.value = walletRepository.listStats(defaultWalletId, refresh).sortedByDescending { it.net }
            }
            catch (e: java.lang.Exception) { }
        }
    }

    private fun loadPayments(refresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _latestPayments.value = walletRepository.listLatestPayments(defaultWalletId, 10, refresh).sortedByDescending { it.paymentTime.toEpochSecond() }
            }
            catch (e: java.lang.Exception) { }
        }
    }

    fun refresh() {
        loadStats(true)
        loadPayments(true)
    }

    companion object {
        // using hard-coded value for simplicity
        const val defaultWalletId = 1
    }
}