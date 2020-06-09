package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jr.census.R
import com.jr.census.adapters.PropertyDetailFragmentAdapter
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_property_general_info.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PropertyGeneralInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_general_info, container, false)
        view.viewPagerInfo.adapter = PropertyDetailFragmentAdapter(childFragmentManager,
            arrayOf(Pair(getString(R.string.info),  PropertyInformationFragment::class),
                Pair(getString(R.string.location),  PropertyLocationFragment::class))
        )
        view.tabLayoutInfo.setupWithViewPager(view.viewPagerInfo)
        return view
    }




}
