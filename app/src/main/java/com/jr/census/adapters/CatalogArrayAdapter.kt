package com.jr.census.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jr.census.models.Catalog

class CatalogArrayAdapter(context: Context,
                          val resource : Int = android.R.layout.simple_dropdown_item_1line) :
    ArrayAdapter<Catalog>(context, resource) {

    private var catalogs : List<Catalog> = listOf()

    fun setList(catalogs : List<Catalog>){
        this.catalogs = catalogs
        notifyDataSetChanged()
    }


    override fun getCount(): Int {
        return catalogs.size
    }

    override fun getItem(position: Int): Catalog? {
        return catalogs[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view : TextView =  super.getDropDownView(position, convertView, parent) as TextView
        view.text = getItem(position)?.name
        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view : View? = convertView
        if(view == null){
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(resource, parent, false)
        }
        val catalog = catalogs[position]
        view?.findViewById<TextView>(android.R.id.text1)?.text = catalog.name ?: ""

        return view!!
    }
}