package com.jr.census.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


abstract class PropertyDetailFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    abstract override fun getItem(position: Int): Fragment

    abstract override fun getCount(): Int

    abstract override fun getPageTitle(position: Int): CharSequence?
}