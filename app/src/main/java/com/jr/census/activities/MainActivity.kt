package com.jr.census.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.fragments.PropertyDetailFragment
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.helpers.OnResultFromWebService
import com.jr.census.models.CatalogsResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_property_detail.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var catalogsRepository: CatalogsRepository



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CensusApplication).appComponent.inject(this)
        val catalogsCallback = ResponseServiceCallback(onCatalogsResult, this)
        if (savedInstanceState == null) {
            if(intent.getBooleanExtra("getCatalogs", false)){
                catalogsRepository.getCatalogs(catalogsCallback)
            }
        }


    }

    private val onCatalogsResult : OnResultFromWebService<CatalogsResponse> =
        object : OnResultFromWebService<CatalogsResponse>{
            override fun onSuccess(result: CatalogsResponse?, statusCode : Int) {
                if(result != null){
                    MainScope().launch {
                        catalogsRepository.saveCatalogs(result)
                    }
                }
            }

            override fun onFailed(t : Throwable?) {
                Log.e("catalogs", "Connection to server failed", t)
            }

        }




    override fun onBackPressed() {
        val currentFragment = fragmentMain.childFragmentManager.fragments.first()
        val mainNavigation = Navigation.findNavController(this, R.id.fragmentMain)
        if(currentFragment is PropertyDetailFragment){
            val navHostDetail = currentFragment.childFragmentManager.findFragmentById(R.id.propertyDetailParentFragment) as NavHostFragment
            if(!navHostDetail.navController.navigateUp()){
                mainNavigation.navigateUp()
            }
        }else{
            super.onBackPressed()
        }

    }
}
