package com.wwe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        parentFragmentManager.setFragmentResultListener(KeyConstant.KEY_SELECTED_COUNTRY, viewLifecycleOwner
//        ) { _, bundle ->
//           view.findViewById<TextView>(R.id.tvContent).text = bundle.getString("name")
//        }
        view.findViewById<TextView>(R.id.tvContent).text = arguments?.getString("name")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i("WWE", "DetailFragment onSaveInstanceState")
    }

    override fun onResume() {
        super.onResume()
        Log.i("WWE", "DetailFragment onResume")
    }
}