package com.jr.census.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import com.jr.census.R
import com.jr.census.CensusApplication
import com.jr.census.di.modules.CatalogsRepository
import com.jr.census.fragments.MainFragment
import com.jr.census.helpers.MAIN_FRAGMENT_TAG
import com.jr.census.helpers.ResponseServiceCallback
import com.jr.census.helpers.OnResultFromWebService
import com.jr.census.helpers.PROPERTIES_FRAGMENT_TAG
import com.jr.census.models.CatalogsResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var catalogsRepository: CatalogsRepository

    private var mainFragment: MainFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as CensusApplication).appComponent.inject(this)
        val catalogsCallback = ResponseServiceCallback(onCatalogsResult, this)
        if (savedInstanceState == null) {
            mainFragment = MainFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFragment!!, MAIN_FRAGMENT_TAG)
                .commitNow()
            if(intent.getBooleanExtra("getCatalogs", false)){
                catalogsRepository.getCatalogs(catalogsCallback)
            }
        }else{
            mainFragment = supportFragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG) as MainFragment
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
        if(mainFragment?.childFragmentManager?.backStackEntryCount ?: 0 > 0 ){
            val fragment = mainFragment?.childFragmentManager?.findFragmentByTag(
                PROPERTIES_FRAGMENT_TAG)
            if(fragment?.isVisible == false){ //main fragment is visible
                mainFragment?.childFragmentManager?.popBackStack()
            }
        }else{
            super.onBackPressed()
        }
    }
}
