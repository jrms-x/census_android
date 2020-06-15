package com.jr.census.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jr.census.R
import com.jr.census.activities.LoginActivity
import com.jr.census.adapters.BlocksAdapter
import com.jr.census.adapters.PropertiesAdapter
import com.jr.census.CensusApplication
import com.jr.census.databinding.BlocksBottomSheetBinding
import com.jr.census.databinding.DialogBlockLayoutBinding
import com.jr.census.databinding.FragmentPropertiesBinding
import com.jr.census.databinding.PropertySearchBinding
import com.jr.census.models.BlockPropertiesCount
import com.jr.census.models.Property
import com.jr.census.view.callback.BlocksListListener
import com.jr.census.view.callback.PropertyListListener
import com.jr.census.viewmodel.MainViewModel
import com.jr.census.viewmodel.factories.ViewModelFactory
import com.jr.census.viewmodel.models.PropertySearchModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.blocks_bottom_sheet.view.*
import kotlinx.android.synthetic.main.fragment_properties.view.*
import java.lang.Exception
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */

interface ShowDialogBlock{
    fun showDialog()
}

class PropertiesFragment : Fragment(),  ShowDialogBlock, PropertyListListener{

    lateinit var viewModel : MainViewModel
    private lateinit var fragmentPropertiesBinding: FragmentPropertiesBinding

    private var bottomSheetView : View? = null

    @Inject
    lateinit var viewModelFactory : ViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        fragmentPropertiesBinding  =  DataBindingUtil.inflate(inflater,R
            .layout.fragment_properties, container, false)
        val view = fragmentPropertiesBinding.root

        view.recyclerProperties.layoutManager = LinearLayoutManager(context)
        view.recyclerProperties.adapter = PropertiesAdapter(requireContext(), listOf(), this)

        view.appbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search_properties_menu -> {
                    searchPropertiesDialog(inflater,container)
                }
                R.id.logout ->{
                    logout()
                }
            }
            true
        }

        view.appbar.setNavigationOnClickListener {
            val bottomSheet : BlocksBottomSheetBinding = DataBindingUtil.inflate(layoutInflater,R.layout.blocks_bottom_sheet, container, false)
            bottomSheet.listBlock = viewModel.blocksListModel
            val dialog = BottomSheetDialog(requireContext())
            bottomSheetView = bottomSheet.root
            bottomSheetView?.recyclerBlock?.layoutManager = LinearLayoutManager(context)
            bottomSheetView?.recyclerBlock?.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            bottomSheetView?.recyclerBlock?.adapter = BlocksAdapter(requireContext(),viewModel.blocksLiveData.value!!,
                object : BlocksListListener {
                    override fun onItemClick(block: BlockPropertiesCount) {
                        viewModel.selectBlockModel.setBlock(block.id.toString())
                        viewModel.saveBlockAndLoadProperties(activity!!)
                        dialog.dismiss()
                    }

                })

            dialog.setContentView(bottomSheetView!!)
            dialog.show()
        }


        return view
    }

    private fun logout() {
        MaterialAlertDialogBuilder(activity).setTitle(R.string.logout).setNegativeButton(R.string.cancel)
        { _: DialogInterface, _: Int ->

        }.setPositiveButton(R.string.accept) { _: DialogInterface?, _: Int ->
            viewModel.sharedPreferencesHelper.setToken(null)
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }.setMessage(R.string.ask_logout).show()

    }

    private fun searchPropertiesDialog(inflater: LayoutInflater, container: ViewGroup?) {

        val bindingDialog : PropertySearchBinding = DataBindingUtil.inflate(inflater, R.layout.property_search, container, false)
        val propertySearchModel = PropertySearchModel()
        bindingDialog.model = propertySearchModel

        MaterialAlertDialogBuilder(activity).setTitle(R.string.search_properties).setNegativeButton(R.string.cancel)
        { _: DialogInterface, _: Int ->

        }.setPositiveButton(R.string.search) { _: DialogInterface?, _: Int ->
            viewModel.searchProperties(propertySearchModel.getName()?.trim(), propertySearchModel.getSerialNumber()?.trim(),
                propertySearchModel.getStreet()?.trim(), propertySearchModel.getNumber()?.trim(), propertySearchModel.getInteriorNumber()?.trim())
        }.setView(bindingDialog.root).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as CensusApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        if(savedInstanceState == null){
            viewModel.getProperties(requireActivity())
        }

        if(viewModel.selectBlockModel.getBlockNumber() ?: -1 < 0){
            viewModel.setMessage(R.string.select_block_message)
            viewModel.propertiesListModel.setLoading(false)
        }

        viewModel.listenerRefreshProperties  = {
            viewModel.getProperties(requireActivity())
        }

        fragmentPropertiesBinding.viewModel = viewModel

        viewModel.propertiesLiveData.observe(viewLifecycleOwner, Observer<List<Property>?>{ list ->
            if(list != null){
                (view?.recyclerProperties?.adapter as PropertiesAdapter).setElements(list)
            }
        })

        viewModel.showDialogBlock = this

        viewModel.blocksLiveData.observe(viewLifecycleOwner, Observer { list ->
            (bottomSheetView?.recyclerBlock?.adapter as BlocksAdapter?)?.setList(viewModel.blocksLiveData.value!!)
            if(list.isEmpty()){
                viewModel.blocksListModel.setMessage(getString(R.string.blocks_not_found))
            }else{
                viewModel.blocksListModel.setMessage(null)
            }
        })

    }


    override fun showDialog(){
        var dialog : AlertDialog? = null
        val bindingDialog : DialogBlockLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_block_layout, null, false)
        bindingDialog.viewModel = viewModel
        viewModel.blockListener = {visible ->
            dialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.isEnabled = visible

        }
        dialog = MaterialAlertDialogBuilder(activity).setTitle(R.string.title_set_block).setNegativeButton(R.string.cancel)
        { _: DialogInterface, _: Int ->

        }.setPositiveButton(R.string.accept) { _: DialogInterface?, _: Int ->
            try {
                viewModel.saveBlockAndLoadProperties(requireActivity())
            }catch (e : Exception){
                Toast.makeText(context, R.string.invalid_block_value, Toast.LENGTH_SHORT).show()
            }
        }.setView(bindingDialog.root).show()
        viewModel.selectBlockModel.evaluateBlock()

    }

    override fun onClickProperty(property: Property) {

        val action = PropertiesFragmentDirections.propertyDetailAction(property)
        val navigation = Navigation.findNavController(requireActivity(), R.id.fragmentMain)
        navigation.navigate(action)

    }


}
