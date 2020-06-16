package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jr.census.R
import com.jr.census.adapters.PropertyCensusRegisterAdapter
import com.jr.census.adapters.PropertyDetailFragmentAdapter
import kotlinx.android.synthetic.main.fragment_property_census_register.view.*

/**
 * A simple [Fragment] subclass.
 */
class PropertyCensusRegister : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_census_register, container, false)
        view.viewPagerCensus.adapter = PropertyCensusRegisterAdapter(childFragmentManager, requireContext())
        view.tabLayoutCensus.setupWithViewPager(view.viewPagerCensus)
        return view
    }

}
