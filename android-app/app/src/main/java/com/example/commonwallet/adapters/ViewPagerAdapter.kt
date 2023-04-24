package com.example.commonwallet.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.commonwallet.fragments.NewPaymentFragment
import com.example.commonwallet.fragments.StatsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewPaymentFragment()
            1 -> StatsFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}
