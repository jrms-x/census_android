package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import javax.inject.Inject

class PropertyHydraulicsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_hydraulics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider((requireActivity() as AppCompatActivity), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)
        print(viewModel)
    }

}