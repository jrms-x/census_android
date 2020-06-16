package com.jr.census.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jr.census.R
import com.jr.census.fragments.PropertyInformationFragment
import com.jr.census.fragments.PropertyLocationFragment

class PropertyAdapterInformation (fragmentManager: FragmentManager, private val context: Context) :
    PropertyDetailFragmentAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PropertyInformationFragment()
            1 -> PropertyLocationFragment()
            else -> throw IllegalStateException("Index not valid")
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.getString(R.string.info)
            1 -> context.getString(R.string.location)
            else -> null
        }
    }
}