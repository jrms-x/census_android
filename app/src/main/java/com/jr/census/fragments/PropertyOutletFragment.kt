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
import com.jr.census.databinding.FragmentPropertyOutletBinding
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_property_outlet.view.*
import javax.inject.Inject


class PropertyOutletFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel

    lateinit var binding : FragmentPropertyOutletBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_outlet, container, false)
        val view = binding.root
        view.spinnerOutletType.adapter = CatalogArrayAdapter(requireContext())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment().requireParentFragment(), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)

        viewModel.outletLiveData.observe(viewLifecycleOwner, Observer {
            val adapter =(view?.spinnerOutletType?.adapter as CatalogArrayAdapter)
            adapter.setList(it)

        })

        binding.viewModel = viewModel
    }


}