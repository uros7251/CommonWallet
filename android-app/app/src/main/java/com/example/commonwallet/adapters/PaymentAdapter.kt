package com.example.commonwallet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.commonwallet.databinding.PaymentItemBinding
import com.example.commonwallet.models.Payment
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZonedDateTime

class PaymentAdapter(private val data: List<Payment>)
    :RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: PaymentItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

        fun bind(payment: Payment) {
            binding.payerName.text = payment.payerName
            binding.description.text = payment.description
            binding.time.text = getTimeAgo(payment.paymentTime)
            binding.amount.text = payment.amount.toString()
        }
        private fun getTimeAgo(time: ZonedDateTime): String {
            val now = ZonedDateTime.now()
            val duration = Duration.between(time, now)
            val seconds = duration.seconds

            if (seconds < 60) {
                return "just now"
            } else if (seconds < 60 * 60) {
                val minutes = seconds / 60
                return "$minutes min"
            } else if (seconds < 60 * 60 * 24) {
                val hours = seconds / (60 * 60)
                return "$hours hr"
            } else if (seconds < 60 * 60 * 24 * 7) {
                val days = seconds / (60 * 60 * 24)
                return "$days days"
            } else if (seconds < 60 * 60 * 24 * 30) {
                val weeks = seconds / (60 * 60 * 24 * 7)
                return "$weeks week"
            } else {
                return "long ago"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PaymentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}