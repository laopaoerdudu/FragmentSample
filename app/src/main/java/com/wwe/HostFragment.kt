package com.wwe

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wwe.databinding.FragmentHostBinding
import com.wwe.single.SingleStackParentFragment

class HostFragment : Fragment(R.layout.fragment_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentHostBinding.bind(view).run {
            singleStack.setOnClickListener {
                parentFragmentManager.commit {
                    addToBackStack(null)
                    replace<SingleStackParentFragment>(R.id.content)
                }
            }

            multipleStack.setOnClickListener {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("WWE", "HostFragment onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i("WWE", "HostFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("WWE", "HostFragment onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("WWE", "HostFragment onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("WWE", "HostFragment onDetach")
    }
}