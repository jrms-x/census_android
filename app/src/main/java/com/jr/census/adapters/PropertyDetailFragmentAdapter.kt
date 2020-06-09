package com.jr.census.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class PropertyDetailFragmentAdapter(fragmentManager: FragmentManager,
                                    private val fragmentList : Array<Pair<CharSequence, KClass<out Fragment>>>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return fragmentList[position].second.primaryConstructor!!.call()
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].first
    }
}