package com.jr.census.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jr.census.CensusApplication
import com.jr.census.R
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PropertyLocationFragment : Fragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory


    lateinit var viewModel : PropertyDetailViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_location, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment().requireParentFragment(),
            viewModelFactory).get(PropertyDetailViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(map: GoogleMap) {
        val location : LatLng = if(viewModel.property.latitude != null && viewModel.property.longitude != null){
            LatLng(viewModel.property.latitude!!, viewModel.property.longitude!!)

        }else{
            LatLng(19.186593785029025,-96.12221483141184)

        }
        val marker = MarkerOptions()
        marker.position(location)
        marker.title(requireContext().getString(R.string.propertyMarker))
        val cameraPosition = CameraPosition.builder().target(location)
            .zoom(18f).bearing(90f).tilt(20f).build()

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        map.addMarker(marker)
        map.setOnMapClickListener {
            val markerOptions = MarkerOptions()
            markerOptions.position(it)
            viewModel.property.latitude = it.latitude
            viewModel.property.longitude = it.longitude
            markerOptions.title(requireContext().getString(R.string.propertyMarker))
            map.clear()
            map.animateCamera(CameraUpdateFactory.newLatLng(it))
            map.addMarker(markerOptions)
        }
    }

}
