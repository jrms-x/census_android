package com.jr.census.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.jr.census.R
import com.jr.census.adapters.PicturesAdapter
import com.jr.census.CensusApplication
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_property_pictures.view.*
import javax.inject.Inject


class PropertyPicturesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_property_pictures, container, false)
        view.recyclerPictures.layoutManager = LinearLayoutManager(requireContext())
        view.recyclerPictures.adapter = PicturesAdapter(requireContext())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment(), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)

        viewModel.picturesLiveData.observe(viewLifecycleOwner, Observer { list ->
            (view?.recyclerPictures?.adapter as PicturesAdapter?)?.setNewList(list)
            if(list.isEmpty()){
                view?.messagePicture?.visibility = View.VISIBLE
            }else{
                view?.messagePicture?.visibility = View.GONE
            }
        })


    }


}
