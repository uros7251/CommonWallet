package com.example.commonwallet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commonwallet.data.repository.WalletRepository
import com.example.commonwallet.models.OutstandingPayment
import com.example.commonwallet.models.Payer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class NewPaymentViewModel @Inject constructor(private val walletRepository: WalletRepository) : ViewModel() {
    private val defaultWalletId: Int = 1

    private val _participants = MutableLiveData<List<Payer>>()
    val participants : LiveData<List<Payer>> = _participants

    private val _selectedPayer = MutableLiveData<Payer>()
    private val selectedPayer: LiveData<Payer> = _selectedPayer

    val selectedAmount = MutableLiveData<String>(10.0.toString())

    private val _descriptionSuggestions = MutableLiveData<List<String>>()
    val descriptionSuggestions: LiveData<List<String>> = _descriptionSuggestions

    private val selectedDescription = MutableLiveData<String>()

    val customDescription = MutableLiveData<String>()

    private val _customDescriptionEnabled = MutableLiveData<Boolean>(false)
    val customDescriptionEnabled: LiveData<Boolean> = _customDescriptionEnabled

    private val _submissionSuccessful = MutableLiveData<Boolean>()
    val submissionSuccessful: LiveData<Boolean> = _submissionSuccessful

    val failure = MutableLiveData<Boolean>()

    val outOfSync = MutableLiveData<Boolean>()

    init {
        try {
            loadWalletParticipants(defaultWalletId)
            loadDescriptionSuggestions(defaultWalletId)
            checkOutOfSync(defaultWalletId)
        }
        catch (e: Exception) { }
        resetFields()
    }

    private fun loadWalletParticipants(walletId: Int) {
        viewModelScope.launch {
            _participants.value = walletRepository.listWalletParticipants(walletId)
        }
    }

    private fun loadDescriptionSuggestions(walletId: Int) {
        viewModelScope.launch {
            _descriptionSuggestions.value = walletRepository.listDescriptionSuggestions(walletId)
        }
    }

    fun checkOutOfSync(walletId: Int = defaultWalletId) {
        viewModelScope.launch {
            outOfSync.value = walletRepository.anyOutstandingPayment(walletId)
        }
    }

    fun resetFields() {
        if (!participants.value.isNullOrEmpty()) {
            _selectedPayer.value = participants.value!![0]
        }
        if (!descriptionSuggestions.value.isNullOrEmpty()) {
            selectedDescription.value = descriptionSuggestions.value!![0]
        }
        selectedAmount.value = ""
        customDescription.value = ""
        _submissionSuccessful.value = false
    }

    fun onPayerSelected(position: Int) {
        _selectedPayer.value = _participants.value!![position]
    }

    fun onDescriptionSuggestionSelected(position: Int) {
        selectedDescription.value = _descriptionSuggestions.value!![position]
        _customDescriptionEnabled.value = selectedDescription.value == "Other"
    }

    fun submitNewPayment() {
        viewModelScope.launch {
            val utcNow = ZonedDateTime.now(ZoneOffset.UTC)
            val payment = OutstandingPayment(
                0,
                defaultWalletId,
                selectedPayer.value!!.id,
                utcNow,
                selectedAmount.value!!.toDouble(),
                if (selectedDescription.value!! == "Other") customDescription.value!! else selectedDescription.value!!)
            val result = walletRepository.submitNewPayment(payment)
            if (result) {
                _submissionSuccessful.value = true
                outOfSync.value = true
                println("Succeeded to submit new payment")
            }
            else {
                failure.value = true
                println("Failed to submit new payment!")
            }
        }
    }

    fun uploadOutstandingPayments() {
        viewModelScope.launch {
            val result = walletRepository.uploadOutstandingPayments(defaultWalletId)
            if (result) {
                outOfSync.value = false
            }
        }
    }
}
