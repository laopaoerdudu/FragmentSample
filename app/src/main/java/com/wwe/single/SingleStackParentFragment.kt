package com.wwe.single

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.commit
import com.wwe.BaseStackFragment
import com.wwe.R
import com.wwe.databinding.FragmentSingleParentBinding
import com.wwe.ext.addOnBackPressedCallback

class SingleStackParentFragment :
    BaseStackFragment<FragmentSingleParentBinding>(R.layout.fragment_single_parent) {
    override fun initBinding(view: View): FragmentSingleParentBinding =
        FragmentSingleParentBinding.bind(view)

    override fun init(savedInstanceState: Bundle?) {
        addOnBackPressedCallback {
            // 注意这里是 childFragmentManager
            isEnabled = childFragmentManager.backStackEntryCount != 0
            if (isEnabled) {
                childFragmentManager.popBackStack()
            } else {
                requireActivity().onBackPressed()
            }
        }

        if (savedInstanceState == null) {
            for (containerId in mStackIds) {
                childFragmentManager.commit {
                    val fragment =
                        SingleStackChildFragment.newInstance(
                            name(containerId),
                            1
                        )
                    replace(containerId, fragment, fragment.stableTag)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("WWE", "SingleStackParentFragment onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i("WWE", "SingleStackParentFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("WWE", "SingleStackParentFragment onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("WWE", "SingleStackParentFragment onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("WWE", "SingleStackParentFragment onDetach")
    }
}