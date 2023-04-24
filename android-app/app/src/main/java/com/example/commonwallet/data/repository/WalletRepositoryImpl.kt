package com.example.commonwallet.data.repository

import com.example.commonwallet.database.AppDatabase
import com.example.commonwallet.models.DescriptionSuggestion
import com.example.commonwallet.models.NewPayment
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.network.CommonWalletApiService
import com.example.commonwallet.viewmodels.Payer
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val walletApi: CommonWalletApiService,
    localDb: AppDatabase) : WalletRepository {

    private val _statsDao = localDb.statsDao()
    private val _payersDao = localDb.payersDao()
    private val _descriptionsDao = localDb.descriptionSuggestionsDao()
    private val _paymentsDao = localDb.paymentsDao()

    private val _cachedParticipants = mutableListOf<Payer>() /* mutableListOf(
        Payer(1, "Uros Stojkovic"),
        Payer(2, "Vuk Bibic"),
        Payer(3, "Lazar Minic")
    )*/

    private val _cachedDescriptions = mutableListOf<String>() /*mutableListOf(
        "HIT",
        "Mensa",
        "Drinking out",
        "Eating out",
        "Other"
    )*/

    private val _cachedStats = mutableListOf<TotalAndNetPaymentStat>() /*mutableListOf(
        TotalAndNetPaymentStat(1, "Uros Stojkovic", 100.0, 10.0),
        TotalAndNetPaymentStat(2, "Vuk Bibic", 80.0, -10.0),
        TotalAndNetPaymentStat(3, "Lazar Minic", 90.0, 0.0)
    )*/

    private val _cachedPayments = mutableListOf<Payment>()

    override suspend fun submitNewPayment(payment: NewPayment): Boolean {
        println("submitNewPayment: $payment")
        var success = false
        withContext(Dispatchers.IO) {
            try {
                walletApi.registerNewPayment(payment)
                success = true
            } catch (e: Exception) {
                println(e.message)
            }
        }
        return success
    }

    override suspend fun listWalletParticipants(walletId: Int, refresh: Boolean): List<Payer> {
        println("listWalletParticipants")
        return withContext(Dispatchers.IO) {
            if (refresh || _payersDao.getAll().isEmpty()) {
                println("Participants: Initiating http request...")
                try {
                    val response = walletApi.listParticipants(walletId)
                    if (response.isSuccessful) {
                        println("listWalletParticipants: Successful http")
                        _cachedParticipants.clear()
                        _cachedParticipants.addAll(response.body()!!)
                        _payersDao.deleteAll()
                        _payersDao.insertAll(_cachedParticipants)
                    } else {
                        println(response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            if (_cachedParticipants.isEmpty()) {
                println("Participants: Reading from local data store")
                _cachedParticipants.clear()
                _cachedParticipants.addAll(_payersDao.getAll())
            }
            _cachedParticipants
        }

    }

    override suspend fun listDescriptionSuggestions(walletId: Int, refresh: Boolean): List<String> {
        println("listDescriptionSuggestions")
        return withContext(Dispatchers.IO) {
            if (refresh || _descriptionsDao.getAll().isEmpty()) {
                println("Descriptions: Initiating http request...")
                try {
                    val response = walletApi.listSuggestedDescriptions(walletId)
                    if (response.isSuccessful) {
                        println("listWalletParticipants: Successful http")
                        _cachedDescriptions.clear()
                        _cachedDescriptions.addAll(response.body()!!)
                        _descriptionsDao.deleteAll()
                        withContext(Dispatchers.IO) {
                            _descriptionsDao.insertAll(_cachedDescriptions.map { desc ->
                                DescriptionSuggestion(
                                    desc
                                )
                            })
                        }
                    } else {
                        println(response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            if (_cachedDescriptions.isEmpty()) {
                println("Descriptions: Reading from local data store")
                _cachedDescriptions.clear()
                _cachedDescriptions.addAll(_descriptionsDao.getAll())
            }
            _cachedDescriptions
        }
    }

    override suspend fun listStats(walletId: Int, refresh: Boolean): List<TotalAndNetPaymentStat> {
        println("listStats")
        return withContext(Dispatchers.IO) {
            if (refresh || _statsDao.getAll().isEmpty()) {
                try {
                    val response = walletApi.listTotalAndNetPayments(walletId)
                    if (response.isSuccessful) {
                        _cachedStats.clear()
                        _cachedStats.addAll(response.body()!!)
                        _statsDao.deleteAll()
                        _statsDao.insertAll(_cachedStats)
                    } else {
                        println("Failed to fetch stats!")
                    }
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
            if (_cachedStats.isEmpty()) {
                println("Reading from local data store")
                _cachedStats.clear()
                _cachedStats.addAll(_statsDao.getAll())
            }
            _cachedStats
        }
    }

    override suspend fun listLatestPayments(walletId: Int, latestN: Int, refresh: Boolean): List<Payment> {
        println("listPayments")
        return withContext(Dispatchers.IO) {
            if (refresh || _paymentsDao.getAll().isEmpty()) {
                try {
                    val response = walletApi.listLatestPayments(walletId, latestN)
                    if (response.isSuccessful) {
                        _cachedPayments.clear()
                        _cachedPayments.addAll(response.body()!!)
                        _paymentsDao.deleteAll()
                        _paymentsDao.insertAll(_cachedPayments)
                    } else {
                        println("Failed to fetch stats!")
                    }
                }
                catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
            if (_cachedPayments.isEmpty()) {
                println("Reading from local data store")
                _cachedPayments.clear()
                val result = withContext(Dispatchers.IO) {
                    _paymentsDao.getAll()
                }
                _cachedPayments.addAll(result)
            }
            _cachedPayments
        }
    }
}