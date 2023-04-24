package com.example.commonwallet.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.commonwallet.viewmodels.Payer

class PayerSpinnerAdapter(private val context: Context, val data: List<Payer>)
    :ArrayAdapter<Payer>(context, android.R.layout.simple_spinner_dropdown_item, data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val item = getItem(position)
        (view as? TextView)?.text = item?.name // Set the text of the view to the name field
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val item = getItem(position)
        (view as? TextView)?.text = item?.name // Set the text of the view to the name field
        return view
    }
}