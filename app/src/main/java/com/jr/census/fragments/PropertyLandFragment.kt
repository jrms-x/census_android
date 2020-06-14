package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.adapters.CatalogArrayAdapter
import com.jr.census.databinding.FragmentPropertyLandBinding
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_property_land.view.*
import javax.inject.Inject


class PropertyLandFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel
    lateinit var binding : FragmentPropertyLandBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_land, container, false)
        val view = binding.root
        view.spinnerPropertyType.adapter = CatalogArrayAdapter(requireContext())
        view.spinnerChargeType.adapter = CatalogArrayAdapter(requireContext())

        view.spinnerAnomalies.adapter = CatalogArrayAdapter(requireContext())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment().requireParentFragment(), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.propertyTypesLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = (view?.spinnerPropertyType?.adapter as CatalogArrayAdapter)
            adapter.setList(it)
        })

        viewModel.chargeTypesLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = (view?.spinnerChargeType?.adapter as CatalogArrayAdapter)
            adapter.setList(it)
        })



        viewModel.anomaliesLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = (view?.spinnerAnomalies?.adapter as CatalogArrayAdapter)
            adapter.setList(it)
        })
    }

}