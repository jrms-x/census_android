package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jr.census.R
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
        view.viewPagerCensus.adapter = PropertyDetailFragmentAdapter(childFragmentManager, arrayOf(
            Pair(getString(R.string.land), PropertyLandFragment::class),
            Pair(getString(R.string.take), PropertyTakeFragment::class),
            Pair(getString(R.string.meter), PropertyMeterFragment::class),
            Pair(getString(R.string.hidraulics), PropertyHydraulicsFragment::class),
            Pair(getString(R.string.survey), PropertySurveyFragment::class),
            Pair(getString(R.string.attachment), PropertyAttachmentFragment::class)
        ))
        view.tabLayoutCensus.setupWithViewPager(view.viewPagerCensus)
        return view
    }

}
