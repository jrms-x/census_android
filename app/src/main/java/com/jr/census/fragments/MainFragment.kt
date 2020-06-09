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
import com.jr.census.databinding.FragmentMainBinding

import com.jr.census.helpers.PROPERTIES_FRAGMENT_TAG
import com.jr.census.viewmodel.MainViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import javax.inject.Inject



class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainViewModelFactory : ViewModelFactory

    lateinit var viewModel: MainViewModel
    private lateinit var binding : FragmentMainBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
        return binding.root
    }





    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)


        binding.viewModel = viewModel


        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().replace(R.id.childContainer, PropertiesFragment(), PROPERTIES_FRAGMENT_TAG).commit()
            viewModel.getProperties(requireActivity())
        }
    }
}