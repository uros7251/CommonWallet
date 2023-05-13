package com.example.commonwallet.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.commonwallet.adapters.PaymentAdapter
import com.example.commonwallet.adapters.StatAdapter
import com.example.commonwallet.databinding.FragmentStatsBinding
import com.example.commonwallet.models.Payment
import com.example.commonwallet.models.TotalAndNetPaymentStat
import com.example.commonwallet.viewmodels.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {
    private lateinit var binding: FragmentStatsBinding
    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStatsBinding.inflate(inflater, container, false)

        binding.statsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.statsRecyclerView.adapter = StatAdapter(listOf())

        val statsObserver = Observer<List<TotalAndNetPaymentStat>> { stats ->
            val adapter = StatAdapter(stats)
            println("Stats: $stats")
            binding.statsRecyclerView.adapter = adapter
        }
        viewModel.statistics.observe(viewLifecycleOwner, statsObserver)

        binding.paymentsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.paymentsRecyclerView.adapter = PaymentAdapter(listOf())

        val paymentsObserver = Observer<List<Payment>> { payments ->
            val adapter = PaymentAdapter(payments)
            println("Payments: $payments")
            binding.paymentsRecyclerView.adapter = adapter
        }
        viewModel.latestPayments.observe(viewLifecycleOwner, paymentsObserver)

        val refreshListener = OnRefreshListener { ->
            viewModel.refresh()
        }
        binding.swipeRefresh.setOnRefreshListener(refreshListener)

        val successObserver = Observer<Boolean> { success ->
            if (success) {
                binding.swipeRefresh.isRefreshing = false
                viewModel.success.value = false
                Toast.makeText(requireContext(), "Successful refresh", Toast.LENGTH_SHORT).show()
                println("Success reload")
            }
        }
        viewModel.success.observe(viewLifecycleOwner, successObserver)

        val failureObserver = Observer<Boolean> { failure ->
            if (failure) {
                binding.swipeRefresh.isRefreshing = false
                viewModel.failure.value = false
                Toast.makeText(requireContext(), "Failed to refresh. Try again in a minute!", Toast.LENGTH_SHORT).show()
                println("Failure reload")
            }
        }
        viewModel.failure.observe(viewLifecycleOwner, failureObserver)

        return binding.root
    }
}