package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.databinding.FragmentPropertyInformationBinding
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PropertyInformationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel

    private lateinit var binding : FragmentPropertyInformationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_information, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment().requireParentFragment(), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)
        binding.viewModel = viewModel
    }

}
