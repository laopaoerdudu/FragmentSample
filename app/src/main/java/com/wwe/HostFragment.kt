package com.wwe

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.wwe.databinding.FragmentHostBinding
import com.wwe.replace.ParentFragment

class HostFragment : BaseFragment<FragmentHostBinding>(R.layout.fragment_host) {
    override fun initBinding(view: View): FragmentHostBinding = FragmentHostBinding.bind(view)

    override fun init(savedInstanceState: Bundle?) {
        binding.button1.setOnClickListener { jumpToFragment<ParentFragment>() }
//        binding.button2.setOnClickListener { jumpToFragment<ViewModelWithSavedStateFragment>() }
//        binding.button3.setOnClickListener { jumpToFragment<ViewModelWithoutSavedStateFragment>() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("WWE", "onCreate: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("WWE", "onDestroy: $this")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("WWE", "onDestroyView: $this")
    }
}

inline fun <reified F : Fragment> Fragment.jumpToFragment() {
    parentFragmentManager.commit {
        addToBackStack(null)
        replace<F>(R.id.container)
    }
}