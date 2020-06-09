package com.jr.census.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.OperationApplicationException
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.databinding.FragmentPropertyDetailBinding
import com.jr.census.helpers.AppBarScrollChange
import com.jr.census.helpers.AppBarScrollState
import com.jr.census.helpers.FILE_AUTHORITY
import com.jr.census.models.Property
import com.jr.census.viewmodel.MainViewModel
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.android.synthetic.main.fragment_property_detail.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import javax.inject.Inject

private const val ARG_PROPERTY = "property"


class PropertyDetailFragment : Fragment(){

    lateinit var property: Property

    @Inject
    lateinit var viewModelFactory:  ViewModelFactory

    lateinit var viewModelParent : MainViewModel
    lateinit var viewModel : PropertyDetailViewModel

    lateinit var fragmentBinding : FragmentPropertyDetailBinding

    private val cameraRequest = 0x007


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            property = it.getParcelable(ARG_PROPERTY)!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_property_detail, container, false)
        val view = fragmentBinding.root
        //view.viewPagerPropertyDetail.adapter = PropertyDetailFragmentAdapter(childFragmentManager)
        //view.tabLayoutPropertyDetail.setupWithViewPager(view.viewPagerPropertyDetail)
        view.bottomNavigationPropertyDetail.setOnNavigationItemSelectedListener {
            goToFragment(it, view.bottomNavigationPropertyDetail.selectedItemId)
            true
        }
        view.toolbarPropertyDetail.setNavigationOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }
        view.appbarLayoutPropertyDetail.addOnOffsetChangedListener(AppBarScrollChange { _, state ->
            if(state == AppBarScrollState.EXPANDED){
                view.fabCameraProperty.show()
            }else{
                view.fabCameraProperty.hide()
            }
        })
        return view
    }

    private fun goToFragment(item: MenuItem, currentId : Int) {
        if(item.itemId != currentId || childFragmentManager.fragments.size <= 0){
            val fragment : Fragment =  when(item.itemId){

                R.id.goToGeneralInformation -> {
                    viewModel.title = getString(R.string.info)
                    view?.toolbarPropertyDetail?.title = viewModel.title
                    PropertyGeneralInfoFragment()
                }

                R.id.goToRegisterCensus -> {
                    viewModel.title = getString(R.string.register_census)
                    view?.toolbarPropertyDetail?.title = viewModel.title
                    PropertyCensusRegister()
                }

                R.id.goToPictures -> {
                    viewModel.title = getString(R.string.pictures)
                    view?.toolbarPropertyDetail?.title = viewModel.title
                    PropertyPicturesFragment()
                }
                else -> throw IllegalStateException("Incorrect id")
            }
            childFragmentManager.beginTransaction().replace(R.id.propertyDetailFragment, fragment).commit()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CensusApplication?)?.appComponent?.inject(this)
        viewModelParent = ViewModelProvider(requireParentFragment(), viewModelFactory).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PropertyDetailViewModel::class.java)
        viewModel.property = property
        viewModel.callCameraFunction = {
            callCamera()
        }
        if(savedInstanceState == null){
            view?.bottomNavigationPropertyDetail?.selectedItemId = R.id.goToGeneralInformation
        }else{
            view?.toolbarPropertyDetail?.title = viewModel.title
        }
        fragmentBinding.viewModel = viewModel


    }

    private fun callCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {intent ->
            intent.resolveActivity(activity?.packageManager!!)?.also {
                viewModel.file = createFileForPicture()
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(requireContext(), FILE_AUTHORITY, viewModel.file!! ))
                startActivityForResult(intent, cameraRequest)
            }

        }
    }

    private fun createFileForPicture(): File {

        val file = File(context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), UUID.randomUUID().toString() + ".jpg")
        if(!file.exists()){
            if(!file.createNewFile()){
                throw OperationApplicationException("Couldn't create file")
            }
        }
        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == cameraRequest){
            if(resultCode != RESULT_OK){
                Log.d("picture", "Picture not taken, deleting file...")
                viewModel.viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        if(viewModel.file?.delete() == true){
                            Log.d("picture", "File deleted")
                        }
                    }
                }
            }else{
                viewModel.viewModelScope.launch {
                    val file = Compressor.compress(requireContext(),viewModel.file!!){
                        default()
                        destination(viewModel.file!!)
                    }
                    println(file)
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(property: Property) =
            PropertyDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PROPERTY, property)
                }
            }
    }


}
