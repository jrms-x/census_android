package com.jr.census.viewmodel.models

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.jr.census.BR
import java.lang.NumberFormatException

object SelectBlockModel : BaseObservable() {

    private var block : String? = null
    var blockListener : ((Boolean) -> Unit)? = null

    @Bindable
    fun getBlock() : String?{
        return block
    }

    @Bindable
    fun setBlock(block : String?){
        if(block != SelectBlockModel.block){
            SelectBlockModel.block = block
            notifyPropertyChanged(BR.block)
            evaluateBlock()
        }
    }

    fun evaluateBlock(){
        try{
            block?.toInt()
            blockListener?.invoke(true)
        }catch (e : NumberFormatException){
            Log.e("block", "exception", e)
            blockListener?.invoke(false)

        }
    }

    fun getBlockNumber() : Int?{
        return try{
            block?.toInt()
        }catch (e : NumberFormatException){
            null

        }
    }


}