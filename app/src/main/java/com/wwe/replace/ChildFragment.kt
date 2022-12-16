package com.wwe.replace

import android.os.Bundle
import android.view.View
import com.wwe.BaseFragment
import com.wwe.R
import com.wwe.databinding.FragentChildBinding

class ChildFragment : BaseFragment<FragentChildBinding>(R.layout.fragent_child) {
    override fun initBinding(view: View): FragentChildBinding = FragentChildBinding.bind(view)

    override fun init(savedInstanceState: Bundle?) {

    }
}
