package com.example.commonwallet.fragments

import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.commonwallet.adapters.PayerSpinnerAdapter
import com.example.commonwallet.databinding.FragmentNewPaymentBinding
import com.example.commonwallet.viewmodels.NewPaymentViewModel
import com.example.commonwallet.viewmodels.Payer
import dagger.hilt.android.AndroidEntryPoint
/**
 * A simple [Fragment] subclass.
 * Use the [NewPaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NewPaymentFragment: Fragment() {
    private lateinit var binding: FragmentNewPaymentBinding
    private val viewModel: NewPaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPaymentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val successObserver = Observer<Boolean> { success ->
            if (success) {
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG).show()
                viewModel.resetFields()
                binding.descSpinner.setSelection(0)
                binding.payersSpinner.setSelection(0)
            }
        }
        viewModel.success.observe(viewLifecycleOwner, successObserver)

        val failureObserver = Observer<Boolean> { failure ->
            if (failure) {
                Toast.makeText(requireContext(), "Failed to submit! Database may be down, try again in a minute!", Toast.LENGTH_LONG).show()
                viewModel.failure.value = false
            }
        }
        viewModel.failure.observe(viewLifecycleOwner, failureObserver)

        val participantsObserver = Observer<List<Payer>> { participants ->
            val payerSpinnerAdapter = PayerSpinnerAdapter(requireContext(), participants)
            binding.payersSpinner.adapter = payerSpinnerAdapter
        }
        viewModel.participants.observe(viewLifecycleOwner, participantsObserver)
        setMoneyInputFilter()

        return binding.root
    }

    private fun setMoneyInputFilter() {
        val maxDigitsBeforeDecimalPoint = 5
        val maxDigitsAfterDecimalPoint = 2

        val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            val builder = StringBuilder(dest)
                .replace(dstart, dend, source.subSequence(start, end).toString())

            val decimalIndex = builder.indexOf('.')
            if (decimalIndex != -1 && builder.length - decimalIndex - 1 > maxDigitsAfterDecimalPoint) {
                return@InputFilter ""
            }

            if (builder.length > maxDigitsBeforeDecimalPoint) {
                return@InputFilter ""
            }

            return@InputFilter null
        }

        binding.numberInput.filters = arrayOf(inputFilter)
    }
}