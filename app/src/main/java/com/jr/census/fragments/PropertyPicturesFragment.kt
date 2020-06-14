package com.jr.census.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.jr.census.R
import com.jr.census.adapters.PicturesAdapter
import com.jr.census.CensusApplication
import com.jr.census.databinding.PictureDataLayoutBinding
import com.jr.census.models.Picture
import com.jr.census.viewmodel.PropertyDetailViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import com.jr.census.viewmodel.models.PictureData
import com.jr.census.viewmodel.models.SYNC
import kotlinx.android.synthetic.main.fragment_property_pictures.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class PropertyPicturesFragment : Fragment(){
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewModel : PropertyDetailViewModel

    private var actionMode : ActionMode? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_property_pictures, container, false)

    }

    private val callbackActionMode = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            if(item?.itemId == R.id.deletePictures){
                MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.deletePicture).setMessage(R.string.confirmDeletePictures)
                    .setNegativeButton(R.string.cancel){ _, _ ->

                    }.setPositiveButton(R.string.accept){ _, _ ->
                        viewModel.deletePictures(requireActivity(),(view?.recyclerPictures?.adapter as PicturesAdapter?)?.getAllSelected())
                    }.show()

            }
            return true
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.picture_action_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            (view?.recyclerPictures?.adapter as PicturesAdapter?)?.deselectAll()
            actionMode = null
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity().application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(requireParentFragment(), viewModelFactory)
            .get(PropertyDetailViewModel::class.java)

        view?.recyclerPictures?.layoutManager = LinearLayoutManager(requireContext())
        view?.recyclerPictures?.adapter = PicturesAdapter(requireContext(), viewModel.pictureListener ,viewModel.viewModelScope)

        viewModel.picturesLiveData.observe(viewLifecycleOwner, Observer { list ->
            (view?.recyclerPictures?.adapter as PicturesAdapter?)?.setNewList(list)
            if(list.isEmpty()){
                view?.messagePicture?.visibility = View.VISIBLE
            }else{
                view?.messagePicture?.visibility = View.GONE
            }
        })

        viewModel.callIsSelectMode = {
            actionMode != null
        }

        viewModel.callActionMode = {
            actionMode = (activity as AppCompatActivity).startSupportActionMode(callbackActionMode)
        }

        viewModel.finishActionMode = {
            actionMode?.finish()
            actionMode = null
        }

        viewModel.callEditPicture = {
            editPictureData(it)
        }

    }

    private fun editPictureData(picture: Picture) {
        val binding : PictureDataLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.picture_data_layout, null, false)
        val pictureData = PictureData(picture.title, picture.subtitle, picture.description)
        binding.model = pictureData
        MaterialAlertDialogBuilder(requireContext()).setTitle(R.string.addPictureInformation).setView(binding.root)
            .setNegativeButton(R.string.cancel){ _, _ ->

            }.setPositiveButton(R.string.accept){ _, _ ->
                picture.title = pictureData.getTitle()?.trim()
                picture.subtitle = pictureData.getSubtitle()?.trim()
                picture.description = pictureData.getDescription()?.trim()
                picture.setSynchronized(SYNC)
                viewModel.updatePicture(picture)
                if(picture.isImageAvailableToUpload()){
                    MainScope().launch {
                        viewModel.uploadPicture(picture, requireActivity())
                    }

                }
            }.show()

    }

}
