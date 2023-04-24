package com.example.commonwallet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.commonwallet.R
import com.example.commonwallet.databinding.StatItemBinding
import com.example.commonwallet.models.TotalAndNetPaymentStat
import java.text.DecimalFormat

class StatAdapter(private val data: List<TotalAndNetPaymentStat>)
    :RecyclerView.Adapter<StatAdapter.ViewHolder>() {

    class ViewHolder(private val binding: StatItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: TotalAndNetPaymentStat) {
            binding.personName.text = stat.name
            binding.total.text = stat.total.toString()
            binding.net.text = stat.net.toString()
            if (stat.net > 0) {
                binding.net.text = "+${stat.net}"
                val color = ContextCompat.getColor(context, R.color.positive)
                binding.net.setTextColor(color)
            }
            else if (stat.net < 0) {
                val color = ContextCompat.getColor(context, R.color.negative)
                binding.net.setTextColor(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StatItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var label = data[position].name
        when (position) {
            0 -> label = "${label} \uD83E\uDD47"
            1 -> label = "${label} \uD83E\uDD48"
            2 -> label = "${label} \uD83E\uDD49"
        }
        holder.bind(TotalAndNetPaymentStat(
            data[position].personId,
            label,
            data[position].total,
            data[position].net))
    }
}