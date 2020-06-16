package com.jr.census.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jr.census.R
import com.jr.census.fragments.PropertyLandFragment
import com.jr.census.fragments.PropertyMeterFragment
import com.jr.census.fragments.PropertyOutletFragment

class PropertyCensusRegisterAdapter(fragmentManager: FragmentManager, private val context : Context)
    : PropertyDetailFragmentAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PropertyLandFragment()
            1 -> PropertyOutletFragment()
            2 -> PropertyMeterFragment()
            else -> throw IllegalStateException("Index not valid")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.getString(R.string.land)
            1 -> context.getString(R.string.take)
            2 -> context.getString(R.string.meter)
            else -> null
        }
    }
}