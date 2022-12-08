package com.wwe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class CountriesFragment : Fragment(R.layout.fragment_countries) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("WWE","CountriesFragment onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i("WWE","CountriesFragment onResume")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("WWE","CountriesFragment onSaveInstanceState")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            parentFragmentManager.setFragmentResult(
                KeyConstant.KEY_SELECTED_COUNTRY, // Same request key FragmentA used to register its listener
                bundleOf("name" to "Roman") // The data to be passed to FragmentA
            )
        }
    }
}