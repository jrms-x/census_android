package com.jr.census.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.OperationApplicationException
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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


class PropertyDetailFragment : Fragment() {

    lateinit var property: Property

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModelParent: MainViewModel
    lateinit var viewModel: PropertyDetailViewModel

    lateinit var fragmentBinding: FragmentPropertyDetailBinding

    private val cameraRequest = 0x007

    var menu: Menu? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_property_detail, container, false)
        val view = fragmentBinding.root

        view.toolbarPropertyDetail?.title = getString(R.string.info)

        view.appbarLayoutPropertyDetail.addOnOffsetChangedListener(AppBarScrollChange { _, state ->
            if (state == AppBarScrollState.EXPANDED) {
                view.fabCameraProperty.show()
            } else {
                view.fabCameraProperty.hide()
            }
        })
        setHasOptionsMenu(true)
        return view
    }

    private fun setTitleAndMenu(selectedId: Int?) {
            when (selectedId) {

                R.id.goToGeneralInformation -> {
                    viewModel.title = getString(R.string.info)
                    PropertyGeneralInfoFragment()
                }

                R.id.goToRegisterCensus -> {
                    viewModel.title = getString(R.string.register_census)
                    PropertyCensusRegister()
                }

                R.id.goToPictures -> {
                    viewModel.title = getString(R.string.pictures)
                    PropertyPicturesFragment()
                }
                else -> throw IllegalStateException("Incorrect id")
            }


        view?.toolbarPropertyDetail?.title = viewModel.title
        menu?.findItem(R.id.saveLocation)?.isVisible = selectedId == R.id.goToGeneralInformation


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_property_menu, menu)
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.savePropertyOption) {
            if (viewModel.property.census != null) {
                Toast.makeText(context, R.string.censusAlreadySaved, Toast.LENGTH_SHORT).show()
            } else {
                MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.confirmSaveCensus)
                    .setMessage(R.string.saveCensusMessage)
                    .setPositiveButton(R.string.accept) { _, _ ->
                        viewModel.saveCensusData(requireActivity())
                    }.setNegativeButton(R.string.cancel) { _, _ ->

                    }.show()
            }

        } else if (item.itemId == R.id.saveLocation) {
            MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.confirmSaveLocation)
                .setMessage(R.string.saveLocationMessage)
                .setPositiveButton(R.string.accept) { _, _ ->
                    viewModel.saveLocation(requireActivity())
                }.setNegativeButton(R.string.cancel) { _, _ ->

                }.show()

        } else if (item.itemId == android.R.id.home) {
            parentFragment?.childFragmentManager?.popBackStack()
        }
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CensusApplication?)?.appComponent?.inject(this)

        property = PropertyDetailFragmentArgs.fromBundle(requireArguments()).propertySelected

        viewModelParent = ViewModelProvider(
            requireParentFragment(),
            viewModelFactory
        ).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PropertyDetailViewModel::class.java)
        fragmentBinding.viewModel = viewModel
        viewModel.startSync = true
        viewModel.property = property
        viewModel.loadCensusData()
        viewModel.callShowPicture = {file ->
            Intent(Intent.ACTION_VIEW).let {
                it.setDataAndType(FileProvider.getUriForFile(requireContext(), FILE_AUTHORITY, file), "image/*")
                startActivity(it)
            }
        }
        viewModel.callCameraFunction = {
            callCamera()
        }
        if (savedInstanceState == null) {
            view?.bottomNavigationPropertyDetail?.selectedItemId = R.id.goToGeneralInformation
        } else {
            view?.toolbarPropertyDetail?.title = viewModel.title
        }

        (requireActivity() as AppCompatActivity).setSupportActionBar(view?.toolbarPropertyDetail)


        viewModel.anomaliesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setAnomalySelection(it)
        })

        viewModel.protectionTypesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setProtectionTypeSelection(it)
        })



        viewModel.propertyTypesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setPropertyTypeSelection(it)
        })

        viewModel.outletLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setOutletTypeSelection(it)
        })

        viewModel.meterStatusLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setMeterStatusSelection(it)
        })

        viewModel.meterBrandsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setMeterBrandsSelection(it)
        })

        viewModel.chargeTypesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.setChargeTypeSelection(it)
        })

        viewModel.picturesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewModel.synchronizePictures(requireActivity())
        })

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.propertyDetailParentFragment) as NavHostFragment
        view?.bottomNavigationPropertyDetail?.setupWithNavController(navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            setTitleAndMenu(destination.id)
        }
        /*view?.bottomNavigationPropertyDetail?.setOnNavigationItemSelectedListener {
            setTitleAndMenu(it, view?.bottomNavigationPropertyDetail?.selectedItemId)

        }*/

    }

    private fun callCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(activity?.packageManager!!)?.also {
                viewModel.file = createFileForPicture()
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(requireContext(), FILE_AUTHORITY, viewModel.file!!)
                )
                startActivityForResult(intent, cameraRequest)
            }

        }
    }

    private fun createFileForPicture(): File {

        val file = File(
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            UUID.randomUUID().toString() + ".jpg"
        )
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw OperationApplicationException("Couldn't create file")
            }
        }
        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == cameraRequest) {
            if (resultCode != RESULT_OK) {
                Log.d("picture", "Picture not taken, deleting file...")
                viewModel.viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        if (viewModel.file?.delete() == true) {
                            Log.d("picture", "File deleted")
                        }
                    }
                }
            } else {
                viewModel.viewModelScope.launch {
                    val file = Compressor.compress(requireContext(), viewModel.file!!) {
                        default()
                        destination(viewModel.file!!)
                    }
                    viewModel.viewModelScope.launch {
                        viewModel.savePicture(file.toString())

                    }
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.setAllPicturesWithErrorToSync()
    }


}
