package com.jr.census.viewmodel.models

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR
import com.jr.census.R

const val SYNC = 0
const val SYNCED = 1
const val ERROR = 2

class PictureSynchronized {
    private var status = SYNC
    private var syncDrawable = R.drawable.autorenew


    fun getSyncDrawable(): Int {
        return syncDrawable
    }

    private fun setSyncDrawable(value: Int) {
        syncDrawable = value
    }

    fun getStatus(): Int {
        return status
    }


    fun setStatus(value: Int) {

        status = value

        setSyncDrawable(
            when (status) {
                SYNC -> R.drawable.autorenew
                SYNCED -> R.drawable.check
                else -> R.drawable.close

            }
        )

    }


}